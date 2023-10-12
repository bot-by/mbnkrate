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
package uk.bot_by.monobank.currency_rate.gson;

import static java.util.Objects.isNull;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import uk.bot_by.monobank.currency_rate.Currency;

/**
 * The <a
 * href="https://github.com/google/gson/blob/master/UserGuide.md#TOC-Custom-Serialization-and-Deserialization">Gson
 * adapter</a> converts the currency numeric code (ISO 4217) to {@link Currency} and vice versa.
 */
public class CurrencyCodeTypeAdapter extends TypeAdapter<Currency> {

  @Override
  public Currency read(JsonReader in) throws IOException {
    if (JsonToken.NULL == in.peek()) {
      in.nextNull();
      return null;
    }

    var numericCode = in.nextInt();
    return Currency.fromNumericCode(numericCode).orElseThrow(
        () -> new IllegalStateException("Unknown currency numeric code: " + numericCode));
  }

  @Override
  public void write(JsonWriter out, Currency value) throws IOException {
    if (isNull(value)) {
      out.nullValue();
    } else {
      out.value(value.getNumericCode());
    }
  }

}
