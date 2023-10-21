package uk.bot_by.mbnk.currency_rate.in_memory_store;

import static feign.mock.HttpMethod.GET;

import feign.Client;
import feign.FeignException;
import feign.Response;
import feign.Target;
import feign.codec.Decoder;
import feign.mock.MockClient;
import feign.mock.MockTarget;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import uk.bot_by.mbnk.currency_rate.core.Currency;
import uk.bot_by.mbnk.currency_rate.core.CurrencyRate;
import uk.bot_by.mbnk.currency_rate.core.CurrencyRateService;
import uk.bot_by.mbnk.currency_rate.core.CurrencyRateServiceProvider;

public class TestCurrencyRateServiceProvider extends CurrencyRateServiceProvider {

  @Override
  protected Client getClient() {
    return new MockClient().ok(GET, "/bank/currency");
  }

  @Override
  protected Decoder getDecoder() {
    return new TestDecoder();
  }

  @Override
  protected Class<? extends CurrencyRateService> getServiceType() {
    return TestCurrencyRateService.class;
  }

  @Override
  protected Target<? extends CurrencyRateService> getTarget() {
    return new MockTarget<>(TestCurrencyRateService.class);
  }

  static class TestCurrencyRate implements CurrencyRate {

    private final Currency sourceCurrency;
    private final Currency targetCurrency;

    TestCurrencyRate(Currency sourceCurrency, Currency targetCurrency) {
      this.sourceCurrency = sourceCurrency;
      this.targetCurrency = targetCurrency;
    }

    @Override
    public Currency getSourceCurrency() {
      return sourceCurrency;
    }

    @Override
    public Currency getTargetCurrency() {
      return targetCurrency;
    }

    @Override
    public Instant getDate() {
      return null;
    }

    @Override
    public BigDecimal getRateBuy() {
      return null;
    }

    @Override
    public BigDecimal getRateCross() {
      return null;
    }

    @Override
    public BigDecimal getRateSell() {
      return null;
    }
  }

  static class TestDecoder implements Decoder {

    @Override
    public Object decode(Response response, Type type) throws FeignException {
      return List.of(new TestCurrencyRate(Currency.USD, Currency.UAH),
          new TestCurrencyRate(Currency.EUR, Currency.UAH),
          new TestCurrencyRate(Currency.EUR, Currency.USD),
          new TestCurrencyRate(Currency.GBP, Currency.UAH));
    }

  }

}
