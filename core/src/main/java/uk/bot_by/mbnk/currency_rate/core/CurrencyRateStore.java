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

import java.util.Set;

/**
 * Currency rate store.
 */
public interface CurrencyRateStore {

  /**
   * Get currency rates.
   * <p>
   * If the store contains a direct currency rate (A =&gt; B) or (B =&gt; A) then returns this one.
   * If the store does not contain a direct currency rate but contains both direct rates (A =&gt;
   * UAH) and (B =&gt; UAH) then returns them two. Returns empty set for other cases: any currency
   * rates are not found or only one direct currency rate is found.
   *
   * @param currencyA
   * @param currencyB
   * @return set of currency rates, can be empty
   */
  Set<CurrencyRate> getCurrencyRates(Currency currencyA, Currency currencyB);

}
