package uk.bot_by.monobank.currency_rate.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.bot_by.monobank.currency_rate.Currency;

@ExtendWith(MockitoExtension.class)
@Tag("fast")
class CurrencyCodeDeserializerTest {

  @Mock
  private DeserializationContext context;
  @Mock
  private JsonParser parser;

  private CurrencyCodeDeserializer deserializer;

  @BeforeEach
  void setUp() {
    deserializer = new CurrencyCodeDeserializer();
  }

  @DisplayName("Read numeric code")
  @Test
  void readHryvnia() throws IOException {
    // given
    when(parser.getIntValue()).thenReturn(980);

    // when
    var currency = deserializer.deserialize(parser, context);

    // then
    verify(parser).getIntValue();

    assertEquals(Currency.UAH, currency);
  }

}