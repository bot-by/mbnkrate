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
package uk.bot_by.mbnk.currency_rate.core;

import java.util.NoSuchElementException;
import java.util.ServiceLoader;

/**
 * Monobank Currency API Factory.
 */
public class CurrencyRateFactory {

  private CurrencyRateFactory() {
  }

  /**
   * Get an instance of Monobank Currency API Service Provider.
   * <p>
   * A provider has to extend {@link CurrencyRateServiceProvider}, the factory uses SPI to find its.
   * There are some implementations that are based on popular JSON frameworks:
   * <ul>
   *   <li>Gson</li>
   *   <li>Jackson</li>
   *   <li>Jackson Jr.</li>
   * </ul>
   *
   * @return a service provider instance
   * @throws NoSuchElementException if there are no API service providers
   * @see <a href="https://www.baeldung.com/java-spi">Baeldung: Java Service Provider Interface</a>
   * @see <a href="https://javadoc.io/doc/uk.bot-by.mbnk/mbnkrate-gson/">Currency Rate Provider:
   * gson</a>
   * @see <a href="https://javadoc.io/doc/uk.bot-by.mbnk/mbnkrate-jackson/">Currency Rate Provider:
   * jackson</a>
   * @see <a href="https://javadoc.io/doc/uk.bot-by.mbnk/mbnkrate-jackson-jr/">Currency Rate
   * Provider: jackson-jr</a>
   */
  public static CurrencyRateServiceProvider getServiceProvider() throws NoSuchElementException {
    var serviceProviders = ServiceLoader.load(CurrencyRateServiceProvider.class);

    return serviceProviders.findFirst()
        .orElseThrow(() -> new NoSuchElementException("No Monobank Currency API providers found"));
  }

  /**
   * Get an instance of Currency Rate Store.
   * <p>
   * A store provider has to extend {@link CurrencyRateStore}, the factory uses SPI to find its.
   * There are some implementations:
   * <ul>
   *   <li>file store</li>
   *   <li>in-memory store</li>
   *   <li>Redis store</li>
   * </ul>
   *
   * @return a store instance
   * @throws NoSuchElementException if there are no store providers
   * @see <a href="https://www.baeldung.com/java-spi">Baeldung: Java Service Provider Interface</a>
   * @see <a href="https://javadoc.io/doc/uk.bot-by.mbnk/mbnkrate-file-store/">Currency Rate
   * Store: file</a>
   * @see <a href="https://javadoc.io/doc/uk.bot-by.mbnk/mbnkrate-in-memory-store/">Currency Rate
   * Store: in-memory</a>
   * @see <a href="https://javadoc.io/doc/uk.bot-by.mbnk/mbnkrate-redis-store/">Currency Rate Store:
   * redis</a>
   */
  public static CurrencyRateStore getStore() {
    var storeProviders = ServiceLoader.load(CurrencyRateStore.class);

    return storeProviders.findFirst()
        .orElseThrow(() -> new NoSuchElementException("No Currency Rate Store providers found"));
  }

}
