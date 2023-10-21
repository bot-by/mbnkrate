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
package uk.bot_by.mbnk.currency_rate.gson;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;
import java.time.Instant;
import uk.bot_by.mbnk.currency_rate.core.Currency;
import uk.bot_by.mbnk.currency_rate.core.CurrencyRate;

/**
 * This implementation to bind {@link CurrencyRate} with Gson.
 */
public class GsonCurrencyRate implements CurrencyRate {

  @JsonAdapter(CurrencyCodeTypeAdapter.class)
  @SerializedName("currencyCodeA")
  private Currency sourceCurrency;
  @JsonAdapter(CurrencyCodeTypeAdapter.class)
  @SerializedName("currencyCodeB")
  private Currency targetCurrency;
  @JsonAdapter(UnixTimeTypeAdapter.class)
  private Instant date;
  private BigDecimal rateBuy;
  private BigDecimal rateCross;
  private BigDecimal rateSell;

  @Override
  public Currency getSourceCurrency() {
    return sourceCurrency;
  }

  @Override
  public Currency getTargetCurrency() {
    return targetCurrency;
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
