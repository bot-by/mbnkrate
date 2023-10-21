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
package uk.bot_by.mbnk.currency_rate.jackson_jr;

import feign.FeignException;
import feign.Headers;
import feign.RequestLine;
import java.util.List;
import uk.bot_by.mbnk.currency_rate.core.CurrencyRateService;

public interface JacksonJrCurrencyRateService extends CurrencyRateService {

  @Override
  @Headers({"User-Agent: @project.artifactId@/@project.version@ (@feign.client@)",
      "Accept: application/json"})
  @RequestLine(GET_CURRENCY_RATES)
  List<JacksonJrCurrencyRate> getCurrencyRates() throws FeignException;

}
