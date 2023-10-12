package uk.bot_by.monobank.currency_rate.jackson;

import feign.RequestLine;
import java.util.List;
import uk.bot_by.monobank.currency_rate.CurrencyRateService;

public interface JacksonCurrencyRateService extends CurrencyRateService {

  @Override
  @RequestLine(GET_BANK_CURRENCY)
  List<JacksonCurrencyRate> getCurrencyRates();

}
