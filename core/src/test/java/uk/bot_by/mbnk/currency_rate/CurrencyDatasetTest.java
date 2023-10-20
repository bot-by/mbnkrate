package uk.bot_by.mbnk.currency_rate;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@Tag("slow")
class CurrencyDatasetTest {

  static private Collection<String> alphabeticCodes;

  @AfterAll
  static void tearDownClass() {
    assertTrue(alphabeticCodes.isEmpty(),
        "All currencies are checked, the collection should be empty: " + alphabeticCodes);
  }

  @BeforeAll
  static void setUpClass() {
    alphabeticCodes = Stream.of(Currency.values()).map(Currency::toString)
        .collect(Collectors.toSet());
  }

  @DisplayName("Check currencies against the currency dataset github.com:datasets/currency-codes")
  @ParameterizedTest(name = "{index} {0}/{1}")
  @CsvFileSource(resources = "codes-all.csv", nullValues = "-", numLinesToSkip = 1)
  void checkAgainstCurrencyDataset(String entity, String currencyName, String alphabeticCode,
      String numericCode, String minorUnit, String withdrawalDate) {
    // given
    assumeFalse(isNull(minorUnit));
    assumeFalse(nonNull(withdrawalDate));
    assumeTrue(alphabeticCodes.remove(alphabeticCode));

    // when
    var currency = assertDoesNotThrow(() -> Currency.valueOf(alphabeticCode), "currency is found");

    // then
    assertAll(entity + '/' + currencyName,
        () -> assertEquals(Integer.valueOf(numericCode), currency.getNumericCode(), "numeric code"),
        () -> assertEquals(Integer.valueOf(minorUnit), currency.getMinorUnit(), "minor unit"));

  }

  @DisplayName("Check currencies against the Monobank Currency API response")
  @Test
  void checkAgainstResponse() throws IOException {
    try (InputStream rateSource = requireNonNull(getClass().getResourceAsStream("rates.json"),
        "JSON source is absent")) {
      // given
      JSONObject rate;
      var rates = new JSONArray(new JSONTokener(rateSource)).iterator();
      var numericCodes = new HashSet<Integer>();

      while (rates.hasNext()) {
        rate = (JSONObject) rates.next();
        numericCodes.add(rate.getInt("currencyCodeA"));
        numericCodes.add(rate.getInt("currencyCodeB"));
      }

      for (var numericCode : numericCodes) {
        // when and then
        assertThat(Currency.fromNumericCode(numericCode), isPresent());
      }
    }
  }

}
