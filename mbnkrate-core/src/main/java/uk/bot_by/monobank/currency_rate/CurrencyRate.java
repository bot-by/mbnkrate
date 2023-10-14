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
package uk.bot_by.monobank.currency_rate;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Currency exchange rates.
 * <p>
 * It presents rates of the currency <em>A</em> to the currency <em>B</em>.
 * <p>
 * There are three exchange rates:
 * <ul>
 *     <li><strong>buy rate</strong> - Monobank buys,</li>
 *     <li><strong>sell rate</strong> - Monobank sells,</li>
 *     <li><strong>cross rate</strong> - cross rate by third currency, usually USD.</li>
 * </ul>
 * <p>
 * There are at least one of them:
 * <ul>
 *     <li>pair of fields <code>rateBuy</code> and <code>rateSell</code></li>
 *     <li>or <code>rateCross</code></li>
 * </ul>
 *
 * @see <a
 * href="https://api.monobank.ua/docs/#tag/Publichni-dani/paths/~1bank~1currency/get">Monobank open
 * API (v2303): Отримання курсів валют</a>
 */
public interface CurrencyRate {

  Currency getCurrencyA();

  Currency getCurrencyB();

  Instant getDate();

  BigDecimal getRateBuy();

  BigDecimal getRateCross();

  BigDecimal getRateSell();

}
