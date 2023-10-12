package uk.bot_by.monobank.currency_rate.jackson;

import feign.Feign;
import feign.http2client.Http2Client;
import feign.jackson.JacksonDecoder;
import org.jetbrains.annotations.VisibleForTesting;
import uk.bot_by.monobank.currency_rate.CurrencyRateService;
import uk.bot_by.monobank.currency_rate.CurrencyRateServiceProvider;

public class JacksonCurrencyRateServiceProvider implements CurrencyRateServiceProvider {

  private final String locator;

  public JacksonCurrencyRateServiceProvider() {
    this(API_MONOBANK_UA);
  }

  @VisibleForTesting
  JacksonCurrencyRateServiceProvider(String locator) {
    this.locator = locator;
  }

  @Override
  public CurrencyRateService getService() {
    return Feign.builder().client(new Http2Client()).decoder(new JacksonDecoder())
        .target(JacksonCurrencyRateService.class, locator);
  }

}
