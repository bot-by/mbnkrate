package uk.bot_by.monobank.currency_rate.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import uk.bot_by.monobank.currency_rate.Currency;

public class CurrencyCodeDeserializer extends JsonDeserializer<Currency> {

  @Override
  public Currency deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    var numericCode = jsonParser.getIntValue();
    return Currency.fromNumericCode(numericCode).orElseThrow(
        () -> new IllegalStateException("Unknown currency numeric code: " + numericCode));
  }

}
