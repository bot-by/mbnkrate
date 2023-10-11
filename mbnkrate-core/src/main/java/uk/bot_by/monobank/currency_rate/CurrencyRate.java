package uk.bot_by.monobank.currency_rate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface CurrencyRate {

  Currency getCurrencyA();

  Currency getCurrencyB();

  LocalDateTime getDate();

  BigDecimal getRateBuy();

  BigDecimal getRateCross();

  BigDecimal getRateSell();

}
