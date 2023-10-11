package uk.bot_by.monobank.currency_rate;

import feign.RequestLine;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ServiceLoader;

/**
 * Monobank Currency API
 */
public interface CurrencyRateService {

  /**
   * Get an instance of Monobank Currency API.
   *
   * @return an API instance
   * @throws NoSuchElementException if there are no API providers
   */
  static CurrencyRateService getInstance() throws NoSuchElementException {
    var serviceProviders = ServiceLoader.load(CurrencyRateService.class);

    return serviceProviders.findFirst()
        .orElseThrow(() -> new NoSuchElementException("No Monobank Currency API providers found"));
  }

  /**
   * Get a list of Monobank's exchange rates.
   * <p>
   * <strong>Important:</strong><br>
   * the data are cached and updated not more than once every 5 minutes.
   *
   * @return list of currency rates
   * @see CurrencyRate
   */
  @RequestLine("GET /bank/currency")
  List<CurrencyRate> getCurrencyRates();

}
