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
package uk.bot_by.mbnk.currency_rate;

import feign.Client;
import feign.Feign;
import feign.codec.Decoder;
import feign.http2client.Http2Client;
import org.jetbrains.annotations.VisibleForTesting;

/**
 * Monobank Currency API Service Provider
 */
public abstract class CurrencyRateServiceProvider {

  public static final String MONOBANK_API = "https://api.monobank.ua/";

  private final String locator;

  public CurrencyRateServiceProvider() {
    this(MONOBANK_API);
  }

  @VisibleForTesting
  protected CurrencyRateServiceProvider(String locator) {
    this.locator = locator;
  }

  /**
   * Get an instance of Monobank Currency API.
   *
   * @return an API instance
   */
  public final CurrencyRateService getService() {
    return Feign.builder().client(getClient()).decoder(getDecoder())
        .target(getServiceType(), locator);
  }

  /**
   * Get Feign client.
   * <p>
   * The base implementation returns {@link Http2Client}. You can rewrite the content of
   * <code>${feign.client}</code> to show the client replacement in the <strong>User-Agent</strong>
   * header.
   *
   * @return Feign client
   */
  protected Client getClient() {
    return new Http2Client();
  }

  /**
   * Get Feign decoder.
   *
   * @return Feign decoder
   */
  protected abstract Decoder getDecoder();

  /**
   * Get interface extends {@link CurrencyRateService}.
   *
   * @return service class
   */
  protected abstract Class<? extends CurrencyRateService> getServiceType();

}
