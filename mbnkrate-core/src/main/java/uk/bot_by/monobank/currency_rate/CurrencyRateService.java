package uk.bot_by.monobank.currency_rate;

import feign.RequestLine;
import java.util.List;

/**
 * Monobank Currency API
 */
public interface CurrencyRateService {

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
