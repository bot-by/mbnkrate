package uk.bot_by.mbnk.currency_rate.in_memory_store;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.bot_by.mbnk.currency_rate.core.Currency;
import uk.bot_by.mbnk.currency_rate.core.CurrencyRate;
import uk.bot_by.mbnk.currency_rate.core.CurrencyRateStore;

@ExtendWith(MockitoExtension.class)
@Tag("fast")
class HeapCurrencyRateStoreFastTest {

  @Mock
  private CurrencyRate dollarRate;
  @Mock
  private CurrencyRate euroRate;
  @Mock
  private CurrencyRate euroToDollarRate;
  @Mock
  private CurrencyRate poundRate;

  private CurrencyRateStore currencyRateStore;

  @BeforeEach
  void setUp() {
    when(dollarRate.getSourceCurrency()).thenReturn(Currency.USD);
    when(dollarRate.getTargetCurrency()).thenReturn(Currency.UAH);
    when(euroRate.getSourceCurrency()).thenReturn(Currency.EUR);
    when(euroRate.getTargetCurrency()).thenReturn(Currency.UAH);
    when(euroToDollarRate.getSourceCurrency()).thenReturn(Currency.EUR);
    when(euroToDollarRate.getTargetCurrency()).thenReturn(Currency.USD);
    when(poundRate.getSourceCurrency()).thenReturn(Currency.GBP);
    when(poundRate.getTargetCurrency()).thenReturn(Currency.UAH);

    currencyRateStore = new HeapCurrencyRateStore(
        () -> List.of(dollarRate, euroRate, euroToDollarRate, poundRate));
  }

  @DisplayName("Direct currency rate: dollar&hryvnia")
  @ParameterizedTest
  @CsvSource({"UAH,USD", "USD,UAH"})
  void directCurrencyRate(String currencyCodeA, String currencyCodeB) {
    // given
    var currencyA = Currency.valueOf(currencyCodeA);
    var currencyB = Currency.valueOf(currencyCodeB);

    // when
    var currencyRates = currencyRateStore.getCurrencyRates(currencyA, currencyB);

    // then
    assertThat(currencyRates, containsInAnyOrder(dollarRate));
  }

  @DisplayName("Direct currency rate: euro&dollar")
  @ParameterizedTest
  @CsvSource({"EUR,USD", "USD,EUR"})
  void directCurrencyRate2(String currencyCodeA, String currencyCodeB) {
    // given
    var currencyA = Currency.valueOf(currencyCodeA);
    var currencyB = Currency.valueOf(currencyCodeB);

    // when
    var currencyRates = currencyRateStore.getCurrencyRates(currencyA, currencyB);

    // then
    assertThat(currencyRates, containsInAnyOrder(euroToDollarRate));
  }

  @DisplayName("Indirect currency rate: dollar&pound")
  @ParameterizedTest
  @CsvSource({"GBP,USD", "USD,GBP"})
  void indirectCurrencyRate(String currencyCodeA, String currencyCodeB) {
    // given
    var currencyA = Currency.valueOf(currencyCodeA);
    var currencyB = Currency.valueOf(currencyCodeB);

    // when
    var currencyRates = currencyRateStore.getCurrencyRates(currencyA, currencyB);

    // then
    assertThat(currencyRates, containsInAnyOrder(dollarRate, poundRate));
  }

  @DisplayName("Currency rate is not found")
  @ParameterizedTest
  @CsvSource({"UAH,USD", "USD,UAH"})
  void currencyRateIsNotFound(String currencyCodeA, String currencyCodeB) {
    // given
    var currencyA = Currency.valueOf(currencyCodeA);
    var currencyB = Currency.valueOf(currencyCodeB);

    currencyRateStore = new HeapCurrencyRateStore(
        () -> List.of(euroRate, euroToDollarRate, poundRate));

    // when
    var currencyRates = currencyRateStore.getCurrencyRates(currencyA, currencyB);

    // then
    assertThat(currencyRates, empty());
  }

  @DisplayName("Indirect currency rate: another currency rate is not found")
  @ParameterizedTest
  @CsvSource({"PLN,USD", "PLN,UAH"})
  void anotherCurrencyRateIsNotFound(String currencyCodeA, String currencyCodeB) {
    // given
    var currencyA = Currency.valueOf(currencyCodeA);
    var currencyB = Currency.valueOf(currencyCodeB);

    // when
    var currencyRates = currencyRateStore.getCurrencyRates(currencyA, currencyB);

    // then
    assertThat(currencyRates, empty());
  }

}