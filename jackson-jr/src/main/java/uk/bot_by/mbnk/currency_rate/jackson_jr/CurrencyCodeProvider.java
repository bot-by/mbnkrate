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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.jr.ob.api.ReaderWriterProvider;
import com.fasterxml.jackson.jr.ob.api.ValueReader;
import com.fasterxml.jackson.jr.ob.api.ValueWriter;
import com.fasterxml.jackson.jr.ob.impl.JSONReader;
import com.fasterxml.jackson.jr.ob.impl.JSONWriter;
import java.io.IOException;
import uk.bot_by.mbnk.currency_rate.core.Currency;

/**
 * The Jackson Jr. <a
 * href="https://cowtowncoder.medium.com/jackson-2-10-jackson-jr-improvements-9eb5bb7b35f">provider</a>
 * converts the currency numeric code (ISO 4217) to {@link Currency} and vice versa.
 */
public class CurrencyCodeProvider extends ReaderWriterProvider {

  @Override
  public ValueReader findValueReader(JSONReader context, Class<?> type) {
    return (Currency.class == type) ? new CurrencyCodeValueReader()
        : super.findValueReader(context, type);
  }

  @Override
  public ValueWriter findValueWriter(JSONWriter context, Class<?> type) {
    return (Currency.class == type) ? new CurrencyCodeValueWriter()
        : super.findValueWriter(context, type);
  }

  public static class CurrencyCodeValueReader extends ValueReader {

    protected CurrencyCodeValueReader() {
      super(Currency.class);
    }

    @Override
    public Object read(JSONReader reader, JsonParser parser) throws IOException {
      var numericCode = parser.getIntValue();
      return Currency.fromNumericCode(numericCode).orElseThrow(
          () -> new IllegalStateException("Unknown currency numeric code: " + numericCode));
    }

  }

  public static class CurrencyCodeValueWriter implements ValueWriter {

    @Override
    public void writeValue(JSONWriter writer, JsonGenerator generator, Object value)
        throws IOException {
      writer.writeValue(((Currency) value).getNumericCode());
    }

    @Override
    public Class<?> valueType() {
      return Currency.class;
    }

  }

}
