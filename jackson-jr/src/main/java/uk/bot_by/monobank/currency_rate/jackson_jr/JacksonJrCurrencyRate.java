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

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import uk.bot_by.monobank.currency_rate.Currency;
import uk.bot_by.monobank.currency_rate.CurrencyRate;

/**
 * This implementation to bind {@link CurrencyRate} with Jackson Jr.
 */
public class JacksonJrCurrencyRate implements CurrencyRate {

  @JsonProperty("currencyCodeA")
  private Currency currencyA;
  @JsonProperty("currencyCodeB")
  private Currency currencyB;
  @JsonProperty("date")
  private Instant date;
  @JsonProperty("rateBuy")
  private BigDecimal rateBuy;
  @JsonProperty("rateCross")
  private BigDecimal rateCross;
  @JsonProperty("rateSell")
  private BigDecimal rateSell;

  @Override
  public Currency getCurrencyA() {
    return currencyA;
  }

  @Override
  public Currency getCurrencyB() {
    return currencyB;
  }

  @Override
  public Instant getDate() {
    return date;
  }

  @Override
  public BigDecimal getRateBuy() {
    return rateBuy;
  }

  @Override
  public BigDecimal getRateCross() {
    return rateCross;
  }

  @Override
  public BigDecimal getRateSell() {
    return rateSell;
  }

}
