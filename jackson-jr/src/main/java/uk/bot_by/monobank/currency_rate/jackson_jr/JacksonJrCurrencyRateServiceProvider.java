/*
 * Copyright 2023 Vitalij Berdinskih
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.bot_by.monobank.currency_rate.jackson_jr;

import com.fasterxml.jackson.jr.annotationsupport.JacksonAnnotationExtension;
import com.fasterxml.jackson.jr.ob.JacksonJrExtension;
import com.fasterxml.jackson.jr.ob.api.ExtensionContext;
import feign.codec.Decoder;
import feign.jackson.jr.JacksonJrDecoder;
import java.util.List;
import org.jetbrains.annotations.VisibleForTesting;
import uk.bot_by.monobank.currency_rate.CurrencyRateService;
import uk.bot_by.monobank.currency_rate.CurrencyRateServiceProvider;

/**
 * A provider creates a service with the {@linkplain JacksonJrDecoder Gson decoder}.
 */
public class JacksonJrCurrencyRateServiceProvider extends CurrencyRateServiceProvider {

  public JacksonJrCurrencyRateServiceProvider() {
    super();
  }

  @VisibleForTesting
  JacksonJrCurrencyRateServiceProvider(String locator) {
    super(locator);
  }

  @Override
  protected Decoder getDecoder() {
    var currencyRateExtension = new JacksonJrExtension() {
      @Override
      protected void register(ExtensionContext context) {
        context.insertProvider(new CurrencyCodeProvider());
        context.insertProvider(new UnixTimeProvider());
      }
    };

    return new JacksonJrDecoder(List.of(JacksonAnnotationExtension.std, currencyRateExtension));
  }

  @Override
  protected Class<? extends CurrencyRateService> getServiceType() {
    return JacksonJrCurrencyRateService.class;
  }

}
