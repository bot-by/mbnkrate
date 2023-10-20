package uk.bot_by.mbnk.currency_rate;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresentAndIs;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@Tag("fast")
class CurrencyTest {

  @DisplayName("Find a currency by its symbol")
  @Test
  void findOneCurrencyByItsSymbol() {
    // given
    var symbol = "₴"; // hryvnia

    // when
    var currencies = Currency.fromSymbol(symbol);

    // then
    assertThat(currencies, containsInAnyOrder(Currency.UAH));
  }

  @DisplayName("Find some currencies with a common symbol")
  @Test
  void findSomeCurrenciesWithCommonSymbol() {
    // given
    var symbol = "¥"; // yen or yuan

    // when
    var currencies = Currency.fromSymbol(symbol);

    // then
    assertThat(currencies, containsInAnyOrder(Currency.CNY, Currency.JPY));
  }

  @DisplayName("Find a currency by its symbols")
  @ParameterizedTest
  @ValueSource(strings = {"Fr", "fr", "SFr"})
  void findOneCurrencyByItsSymbols(String symbol) {
    // when
    var currencies = Currency.fromSymbol(symbol);

    // then
    assertThat("Symbol " + symbol, currencies, containsInAnyOrder(Currency.CHF));
  }

  @DisplayName("Find a currency by its numeric code")
  @Test
  void findCurrencyByCode() {
    // given
    var numericCode = 980; // hryvnia

    // when
    var currency = Currency.fromNumericCode(numericCode);

    // then
    assertThat(currency, isPresentAndIs(Currency.UAH));
  }

  @DisplayName("No currency with character")
  @Test
  void currencyIsNotFoundByCharacter() {
    // given
    var symbol = ":";

    // when
    var currencies = Currency.fromSymbol(symbol);

    // then
    assertThat(currencies, empty());
  }

  @DisplayName("No currency with numeric code")
  @Test
  void currencyIsNotFoundByCode() {
    // given
    var numericCode = 0;

    // when
    var currency = Currency.fromNumericCode(numericCode);

    // then
    assertThat(currency, isEmpty());
  }

}