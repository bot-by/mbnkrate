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
package uk.bot_by.mbnk.currency_rate.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import uk.bot_by.mbnk.currency_rate.Currency;
import uk.bot_by.mbnk.currency_rate.CurrencyRate;

/**
 * This implementation to bind {@link CurrencyRate} with Jackson.
 */
public class JacksonCurrencyRate implements CurrencyRate {

  @JsonDeserialize(using = CurrencyCodeDeserializer.class)
  @JsonProperty("currencyCodeA")
  private Currency currencyA;
  @JsonDeserialize(using = CurrencyCodeDeserializer.class)
  @JsonProperty("currencyCodeB")
  private Currency currencyB;
  @JsonDeserialize(using = UnixTimeDeserializer.class)
  private Instant date;
  private BigDecimal rateBuy;
  private BigDecimal rateCross;
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
