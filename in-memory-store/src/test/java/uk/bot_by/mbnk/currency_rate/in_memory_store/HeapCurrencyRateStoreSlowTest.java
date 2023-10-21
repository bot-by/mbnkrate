package uk.bot_by.mbnk.currency_rate.in_memory_store;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.CombinableMatcher.both;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import uk.bot_by.mbnk.currency_rate.core.Currency;
import uk.bot_by.mbnk.currency_rate.core.CurrencyRate;
import uk.bot_by.mbnk.currency_rate.core.CurrencyRateFactory;
import uk.bot_by.mbnk.currency_rate.core.CurrencyRateStore;

@ExtendWith(MockitoExtension.class)
@Tag("slow")
class HeapCurrencyRateStoreSlowTest {

  private static CurrencyRateStore currencyRateStore;

  @DisplayName("Get a store")
  @BeforeAll
  static void getStore() {
    // when
    currencyRateStore = assertDoesNotThrow(CurrencyRateFactory::getStore);

    // then
    var logger = LoggerFactory.getLogger(HeapCurrencyRateStore.class);

    verify(logger).debug(eq("Monobank gives {} rates"), eq(4));
    verify(logger).debug(eq("Currency rates was updated"));
    assertAll("Store", () -> assertNotNull(currencyRateStore),
        () -> assertTrue(currencyRateStore instanceof HeapCurrencyRateStore));
  }

  @BeforeEach
  void setUp() {
    clearInvocations(LoggerFactory.getLogger(HeapCurrencyRateStore.class));
  }

  @DisplayName("Get direct currency rate: dollar&hryvnia")
  @Test
  void directCurrencyRate() {
    // given
    var logger = LoggerFactory.getLogger(HeapCurrencyRateStore.class);

    when(logger.isDebugEnabled()).thenReturn(true);

    // when
    var currencyRates = currencyRateStore.getCurrencyRates(Currency.UAH, Currency.USD);

    // then
    verify(logger).isDebugEnabled();
    verify(logger).debug(eq("direct currency rate {}"), isA(CurrencyRate.class));
    verifyNoMoreInteractions(logger);
    assertAll("Direct currency rate", () -> assertThat(currencyRates, hasSize(1)),
        () -> assertThat(currencyRates, containsInAnyOrder(
            both(hasProperty("sourceCurrency", equalTo(Currency.USD))).and(
                hasProperty("targetCurrency", equalTo(Currency.UAH))))));
  }

  @DisplayName("Get indirect currency rate: dollar&pound")
  @Test
  void indirectCurrencyRates() {
    // given
    var logger = LoggerFactory.getLogger(HeapCurrencyRateStore.class);

    when(logger.isDebugEnabled()).thenReturn(true);

    // when
    var currencyRates = currencyRateStore.getCurrencyRates(Currency.USD, Currency.GBP);

    // then

    verify(logger).isDebugEnabled();
    verify(logger).debug(eq("indirect currency rate {}, {}"), isA(CurrencyRate.class),
        isA(CurrencyRate.class));
    verifyNoMoreInteractions(logger);
    assertAll("Indirect currency rates", () -> assertThat(currencyRates, hasSize(2)),
        () -> assertThat(currencyRates,
            containsInAnyOrder(hasProperty("sourceCurrency", equalTo(Currency.USD)),
                hasProperty("sourceCurrency", equalTo(Currency.GBP)))));
  }

  @DisplayName("Currency rate is not found")
  @Test
  void currencyRateIsNotFound() {
    // given
    var logger = LoggerFactory.getLogger(HeapCurrencyRateStore.class);

    when(logger.isDebugEnabled()).thenReturn(true);

    // when
    var currencyRates = currencyRateStore.getCurrencyRates(Currency.PLN, Currency.UAH);

    // then
    verify(logger, never()).isDebugEnabled();
    verify(logger).info(eq("currency rate {}/{} is not found"), eq(Currency.PLN), eq(Currency.UAH));
    verifyNoMoreInteractions(logger);
    assertThat("currency rate set is empty", currencyRates, empty());
  }

  @DisplayName("Indirect currency rate: another currency rate is not found")
  @Test
  void anotherCurrencyRateIsNotFound() {
    // given
    var logger = LoggerFactory.getLogger(HeapCurrencyRateStore.class);

    when(logger.isDebugEnabled()).thenReturn(true);

    // when
    var currencyRates = currencyRateStore.getCurrencyRates(Currency.PLN, Currency.USD);

    // then
    verify(logger, never()).isDebugEnabled();
    verify(logger).info(eq("currency rate {}/{} is not found"), eq(Currency.PLN), eq(Currency.USD));
    verifyNoMoreInteractions(logger);
    assertThat("currency rate set is empty", currencyRates, empty());
  }

}