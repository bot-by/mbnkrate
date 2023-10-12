package uk.bot_by.monobank.currency_rate.gson;

import static java.util.Objects.requireNonNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.text.MatchesPattern.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import feign.FeignException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import uk.bot_by.monobank.currency_rate.Currency;
import uk.bot_by.monobank.currency_rate.CurrencyRateService;

@ExtendWith(MockServerExtension.class)
@Tag("slow")
class GsonTest {

  private static final String MOCKSERVER_LOCATOR = "http://localhost:%s/";

  private CurrencyRateService currencyRateService;

  @BeforeEach
  void setUp(MockServerClient mockServerClient) {
    currencyRateService = (new GsonCurrencyRateServiceProvider(
        String.format(MOCKSERVER_LOCATOR, mockServerClient.getPort()))).getService();

    mockServerClient.reset();
  }

  @DisplayName("Get currency rates")
  @Test
  void currencyRates(MockServerClient mockServerClient) throws IOException {
    try (InputStream rateSource = requireNonNull(getClass().getResourceAsStream("rates.json"),
        "JSON source is absent")) {
      // given
      String responseBody = new BufferedReader(
          new InputStreamReader(rateSource, StandardCharsets.UTF_8)).lines()
          .collect(Collectors.joining("\n"));

      mockServerClient.when(request("/bank/currency").withMethod("GET")).respond(
          response().withHeader("content-type", "application/json; charset=utf-8")
              .withBody(responseBody));

      // when
      var currencyRates = currencyRateService.getCurrencyRates();

      // then
      mockServerClient.verify(request("/bank/currency"));

      assertAll("Currency rates", () -> assertNotNull(currencyRates, "rates"),
          () -> assertThat("list size", currencyRates, hasSize(1)));

      var dollarRate = currencyRates.listIterator().next();

      assertAll("UAH/USD rate",
          () -> assertEquals(Currency.USD, dollarRate.getCurrencyA(), "US dollar"),
          () -> assertEquals(Currency.UAH, dollarRate.getCurrencyB(), "hryvnia"),
          () -> assertEquals(Instant.ofEpochSecond(1696918206), dollarRate.getDate(), "date"),
          () -> assertEquals(BigDecimal.valueOf(36.57), dollarRate.getRateBuy(), "buy"),
          () -> assertEquals(BigDecimal.valueOf(0), dollarRate.getRateCross(), "cross"),
          () -> assertEquals(BigDecimal.valueOf(37.4406), dollarRate.getRateSell(), "sell"));
    }
  }

  @DisplayName("Missed fields")
  @Test
  void missedFields(MockServerClient mockServerClient) throws IOException {
    try (InputStream rateSource = requireNonNull(
        getClass().getResourceAsStream("missed_fields.json"), "JSON source is absent")) {
      // given
      String responseBody = new BufferedReader(
          new InputStreamReader(rateSource, StandardCharsets.UTF_8)).lines()
          .collect(Collectors.joining("\n"));

      mockServerClient.when(request("/bank/currency").withMethod("GET")).respond(
          response().withHeader("content-type", "application/json; charset=utf-8")
              .withBody(responseBody));

      // when
      var currencyRates = currencyRateService.getCurrencyRates();

      // then
      mockServerClient.verify(request("/bank/currency"));

      assertAll("Currency rates", () -> assertNotNull(currencyRates, "rates"),
          () -> assertThat("list size", currencyRates, hasSize(1)));

      var dollarRate = currencyRates.listIterator().next();

      assertAll("UAH/USD rate",
          () -> assertEquals(Currency.USD, dollarRate.getCurrencyA(), "US dollar"),
          () -> assertEquals(Currency.UAH, dollarRate.getCurrencyB(), "hryvnia"),
          () -> assertEquals(Instant.ofEpochSecond(1696918206), dollarRate.getDate(), "date"),
          () -> assertNull(dollarRate.getRateBuy(), "buy"),
          () -> assertEquals(BigDecimal.valueOf(36.4385), dollarRate.getRateCross(), "cross"),
          () -> assertNull(dollarRate.getRateSell(), "sell"));
    }
  }

  @DisplayName("Too many requests")
  @Test
  void tooManyRequests(MockServerClient mockServerClient) throws IOException {
    try (InputStream rateSource = requireNonNull(
        getClass().getResourceAsStream("too_many_requests.json"), "JSON source is absent")) {
      // given
      var messagePattern = "\\[429 Mocked\\] during \\[GET\\] to \\[(.+)/bank/currency\\] \\[(.+)#getCurrencyRates\\(\\)\\]: \\[\\{\"errorDescription\":\"Too many requests\"\\}\\]";
      String responseBody = new BufferedReader(
          new InputStreamReader(rateSource, StandardCharsets.UTF_8)).lines()
          .collect(Collectors.joining("\n"));

      mockServerClient.when(request("/bank/currency").withMethod("GET")).respond(
          response().withStatusCode(429)
              .withHeader("content-type", "application/json; charset=utf-8")
              .withHeader("reason-phrase", "Mocked").withBody(responseBody));

      // when
      var exception = assertThrows(FeignException.class,
          () -> currencyRateService.getCurrencyRates());

      // then
      mockServerClient.verify(request("/bank/currency"));

      assertAll("Too many requests", () -> assertEquals(429, exception.status(), "status"),
          () -> assertThat("message", exception.getMessage(), matchesPattern(messagePattern)));
    }
  }

}