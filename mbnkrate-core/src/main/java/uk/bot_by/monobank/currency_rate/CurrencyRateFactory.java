package uk.bot_by.monobank.currency_rate;

import java.util.NoSuchElementException;
import java.util.ServiceLoader;

/**
 * Monobank Currency API Factory
 */
public class CurrencyRateFactory {

  private CurrencyRateFactory() {
  }

  /**
   * Get an instance of Monobank Currency API Service Provider.
   *
   * @return a service provider instance
   * @throws NoSuchElementException if there are no API service providers
   */
  static CurrencyRateServiceProvider getServiceProvider() throws NoSuchElementException {
    var serviceProviders = ServiceLoader.load(CurrencyRateServiceProvider.class);

    return serviceProviders.findFirst()
        .orElseThrow(() -> new NoSuchElementException("No Monobank Currency API providers found"));
  }


}
