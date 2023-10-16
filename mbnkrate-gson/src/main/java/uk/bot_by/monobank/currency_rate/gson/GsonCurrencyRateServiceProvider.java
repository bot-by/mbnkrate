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
package uk.bot_by.monobank.currency_rate.gson;

import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import org.jetbrains.annotations.VisibleForTesting;
import uk.bot_by.monobank.currency_rate.CurrencyRateService;
import uk.bot_by.monobank.currency_rate.CurrencyRateServiceProvider;

public class GsonCurrencyRateServiceProvider extends CurrencyRateServiceProvider {

  public GsonCurrencyRateServiceProvider() {
    super();
  }

  @VisibleForTesting
  GsonCurrencyRateServiceProvider(String locator) {
    super(locator);
  }

  @Override
  protected Decoder getDecoder() {
    return new GsonDecoder();
  }

  @Override
  protected Class<? extends CurrencyRateService> getApiType() {
    return GsonCurrencyRateService.class;
  }

}
