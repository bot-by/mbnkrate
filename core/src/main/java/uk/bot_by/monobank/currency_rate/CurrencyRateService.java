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

import java.util.List;

/**
 * Monobank Currency API
 */
public interface CurrencyRateService {

  String GET_CURRENCY_RATES = "GET /bank/currency";

  /**
   * Get a list exchange rates of Monobank.
   * <p>
   * <strong>Important:</strong><br>
   * the data must be cached and updated not more than once every 5 minutes.
   *
   * @return list of currency rates
   * @see <a
   * href="https://api.monobank.ua/docs/#tag/Publichni-dani/paths/~1bank~1currency/get">Monobank
   * open API (v2303): Отримання курсів валют</a>
   */
  List<? extends CurrencyRate> getCurrencyRates();

}
