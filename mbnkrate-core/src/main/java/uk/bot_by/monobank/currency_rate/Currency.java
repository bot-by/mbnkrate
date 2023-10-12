/*
 * Copyright 2023 Vitalij Berdinskih
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.bot_by.monobank.currency_rate;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Currency {

  UAH(980, "₴"), USD(840, "$", "US$", "U$"), EUR(978, "€"), GBP(826, "£"), JPY(392, 0, "¥"), CHF(
      756, "Fr", "fr", "SFr"), CNY(156, "¥"), AED(784, "DH", "Dh", "Dhs"), AFN(971, "Af",
      "Afs"), ALL(8, "L"), AMD(51, "֏"), AOA(973, "Kz"), ARS(32, "$", "Arg$"), AUD(36, "$", "A$",
      "AU$"), AZN(944, "₼"), BDT(50, "৳"), BGN(975, "лв"), BHD(48, 3, "BD"), BIF(108, 0,
      "FBu"), BND(96, "$", "B$"), BOB(68, "Bs"), BRL(986, "R$"), BWP(72, "P"), BYN(933, "руб", "р",
      "Rbl"), CAD(124, "$", "Can$", "CA$", "C$"), CDF(976, "FC"), CLP(152, 0, "$"), COP(170,
      "$"), CRC(188, "₡"), CUP(192, "$", "$MN"), CZK(203, "Kč"), DJF(262, 0, "Fdj"), DKK(208,
      "kr"), DZD(12, "DA"), EGP(818, "£", "E£", "£E", "LE"), ETB(230, "Br"), GEL(981, "₾"), GHS(936,
      "GH₵"), GMD(270, "D"), GNF(324, 0, "FG"), HKD(344, "$", "HK$"), HRK(191, "kn"), HUF(348,
      "Ft"), IDR(360, "Rp"), ILS(376, "₪"), INR(356, "₹"), IQD(368, 3, "ID"), ISK(352, 0,
      "kr"), JOD(400, 3), KES(404), KGS(417, "\u20C0"), KHR(116), KRW(410, 0, "₩"), KWD(414, 3,
      "KD"), KZT(398, "₸"), LAK(418, "₭", "₭N"), LBP(422, "LL"), LKR(144, "Re", "Rs"), LYD(434, 3,
      "LD"), MAD(504, "DH"), MDL(498, "L"), MGA(969, "Ar"), MKD(807, "den"), MNT(496, "₮"), MUR(480,
      "Re", "Rs"), MWK(454, "K"), MXN(484, "$", "Mex$"), MYR(458, "RM"), MZN(943, "MT", "MTn"), NAD(
      516, "$", "N$"), NGN(566, "₦"), NIO(558, "C$"), NOK(578, "kr"), NPR(524, "रु"), NZD(554,
      "$"), OMR(512, 3, "RO"), PEN(604, "S/"), PHP(608, "₱"), PKR(586, "Re", "Rs"), PLN(985,
      "zł"), PYG(600, 0, "₲", "Gs"), QAR(634, "QR"), RON(946), RSD(941, "дин", "DIN"), SAR(682,
      "SAR", "SR"), SCR(690, "Re", "Rs"), SDG(938, "LS"), SEK(752, "kr"), SGD(702, "$", "S$"), SLL(
      694, "Le"), SOS(706), SRD(968, "$", "Sur$"), SZL(748, "L", "E"), THB(764, "฿"), TJS(972,
      "SM"), TND(788, 3, "DT"), TRY(949, "₺"), TWD(901, "$", "NT$"), TZS(834, "TSh"), UGX(800, 0,
      "USh"), UYU(858, "$", "$U"), UZS(860), VND(704, 0, "₫", "đ"), XAF(950, 0), XOF(952, 0), YER(
      886), ZAR(710, "R");

  private static final int TWO_DIGITS = 2;
  private static final Map<String, Set<Currency>> SYMBOL_TO_ENUM = Stream.of(values())
      .filter(currency -> !currency.getSymbols().isEmpty()).flatMap(
          currency -> currency.symbols.stream()
              .map(symbol -> getSymbolCurrencyEntry(currency, symbol))).collect(
          Collectors.toMap(Entry<String, Currency>::getKey, (entry) -> Set.of(entry.getValue()),
              (first, second) -> Stream.of(first, second).flatMap(Set::stream)
                  .collect(Collectors.toSet())));
  private static final Map<Integer, Currency> NUMERIC_CODE_TO_ENUM = Stream.of(values())
      .collect(Collectors.toMap(Currency::getNumericCode, currency -> currency));

  private final List<String> symbols;
  private final int numericCode;
  private final int minorUnit;

  Currency(int numericCode, int minorUnit, String... symbols) {
    this.symbols = List.of(symbols);
    this.numericCode = numericCode;
    this.minorUnit = minorUnit;
  }

  Currency(int numericCode, String... symbols) {
    this(numericCode, TWO_DIGITS, symbols);
  }

  Currency(int numericCode, int minorUnit) {
    this(numericCode, minorUnit, new String[0]);
  }

  Currency(int numericCode) {
    this(numericCode, TWO_DIGITS, new String[0]);
  }

  public static Set<Currency> fromSymbol(String symbol) {
    return SYMBOL_TO_ENUM.getOrDefault(symbol, Set.of());
  }

  public static Optional<Currency> fromNumericCode(int numericCode) {
    return Optional.ofNullable(NUMERIC_CODE_TO_ENUM.get(numericCode));
  }

  private static Entry<String, Currency> getSymbolCurrencyEntry(Currency currency, String symbol) {
    return new Entry<>() {
      @Override
      public String getKey() {
        return symbol;
      }

      @Override
      public Currency getValue() {
        return currency;
      }

      @Override
      public Currency setValue(Currency currency) {
        return null;
      }

    };
  }

  public List<String> getSymbols() {
    return symbols;
  }

  public int getNumericCode() {
    return numericCode;
  }

  public int getMinorUnit() {
    return minorUnit;
  }

}
