package uk.bot_by.monobank.currency_rate.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import uk.bot_by.monobank.currency_rate.Currency;
import uk.bot_by.monobank.currency_rate.CurrencyRate;

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
