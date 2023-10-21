package uk.bot_by.mbnk.currency_rate.in_memory_store;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

import feign.FeignException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.bot_by.mbnk.currency_rate.core.Currency;
import uk.bot_by.mbnk.currency_rate.core.CurrencyRate;
import uk.bot_by.mbnk.currency_rate.core.CurrencyRateFactory;
import uk.bot_by.mbnk.currency_rate.core.CurrencyRateService;
import uk.bot_by.mbnk.currency_rate.core.CurrencyRateStore;

public final class HeapCurrencyRateStore implements CurrencyRateStore {

  private static final int MONOBANK_API_DELAY = 6;
  private static final Logger LOGGER = LoggerFactory.getLogger(HeapCurrencyRateStore.class);

  private final CurrencyRateService currencyRateService;
  private final Map<Currency, Map<Currency, CurrencyRate>> currencyRateStore;

  public HeapCurrencyRateStore() {
    this(CurrencyRateFactory.getServiceProvider().getService());
  }

  @VisibleForTesting
  HeapCurrencyRateStore(CurrencyRateService currencyRateService) {
    this.currencyRateService = currencyRateService;
    currencyRateStore = new HashMap<>(Currency.values().length + 1); // + USD/EUR
    updateStore();
    newSingleThreadScheduledExecutor().scheduleWithFixedDelay(this::updateStore, MONOBANK_API_DELAY,
        MONOBANK_API_DELAY, TimeUnit.MINUTES);
  }

  @Override
  public Set<CurrencyRate> getCurrencyRates(Currency currencyA, Currency currencyB) {
    final Set<CurrencyRate> currencyRates = new HashSet<>();

    if (currencyRateStore.getOrDefault(currencyA, Map.of()).containsKey(currencyB)) {
      currencyRates.add(currencyRateStore.get(currencyA).get(currencyB));
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("direct currency rate {}", currencyRates.iterator().next());
      }
    }
    if (currencyRates.isEmpty()) {
      if (currencyRateStore.getOrDefault(currencyA, Map.of()).containsKey(Currency.UAH)
          && currencyRateStore.getOrDefault(currencyB, Map.of()).containsKey(Currency.UAH)) {
        currencyRates.add(currencyRateStore.get(currencyA).get(Currency.UAH));
        currencyRates.add(currencyRateStore.get(currencyB).get(Currency.UAH));
        if (LOGGER.isDebugEnabled()) {
          var iterator = currencyRates.iterator();
          LOGGER.debug("indirect currency rate {}, {}", iterator.next(), iterator.next());
        }
      } else {
        LOGGER.info("currency rate {}/{} is not found", currencyA, currencyB);
      }
    }

    return Set.copyOf(currencyRates);
  }

  void updateStore() {
    Map<Currency, CurrencyRate> entry;
    var updatedStore = new HashMap<Currency, Map<Currency, CurrencyRate>>();
    try {
      var currencyRates = currencyRateService.getCurrencyRates();

      LOGGER.debug("Monobank gives {} rates", currencyRates.size());
      for (var currencyRate : currencyRates) {
        // A -> B
        entry = updatedStore.computeIfAbsent(currencyRate.getSourceCurrency(),
            (sourceCurrency) -> new HashMap<>());
        entry.put(currencyRate.getTargetCurrency(), currencyRate);
        // B -> A
        entry = updatedStore.computeIfAbsent(currencyRate.getTargetCurrency(),
            (targetCurrency) -> new HashMap<>());
        entry.put(currencyRate.getSourceCurrency(), currencyRate);
      }

      synchronized (currencyRateStore) {
        currencyRateStore.clear();
        currencyRateStore.putAll(updatedStore);
        LOGGER.debug("Currency rates was updated");
      }
    } catch (FeignException exception) {
      LOGGER.warn("Currency rates was not updated: {}", exception.getMessage());
    }
  }

}
