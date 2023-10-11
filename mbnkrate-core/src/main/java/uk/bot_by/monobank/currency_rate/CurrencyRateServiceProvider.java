package uk.bot_by.monobank.currency_rate;

/**
 * Monobank Currency API Service Provider
 */
public interface CurrencyRateServiceProvider {

  /**
   * Get an instance of Monobank Currency API.
   *
   * @return an API instance
   */
  CurrencyRateService getService();

}
