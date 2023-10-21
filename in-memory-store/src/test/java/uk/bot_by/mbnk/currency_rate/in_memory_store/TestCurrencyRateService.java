package uk.bot_by.mbnk.currency_rate.in_memory_store;

import feign.Headers;
import feign.RequestLine;
import java.util.List;
import uk.bot_by.mbnk.currency_rate.core.CurrencyRateService;
import uk.bot_by.mbnk.currency_rate.in_memory_store.TestCurrencyRateServiceProvider.TestCurrencyRate;

public interface TestCurrencyRateService extends CurrencyRateService {

  @Override
  @Headers({"User-Agent: @project.artifactId@/@project.version@ (@feign.client@)",
      "Accept: application/json"})
  @RequestLine(GET_CURRENCY_RATES)
  List<TestCurrencyRate> getCurrencyRates();

}
