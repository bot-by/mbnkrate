package uk.bot_by.mbnk.currency_rate.jackson_jr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.jr.ob.impl.JSONReader;
import com.fasterxml.jackson.jr.ob.impl.JSONWriter;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.bot_by.mbnk.currency_rate.Currency;

@ExtendWith(MockitoExtension.class)
@Tag("fast")
class CurrencyCodeProviderTest {

  @Mock
  private JsonGenerator generator;
  @Mock
  private JsonParser parser;
  @Mock
  private JSONReader reader;
  @Mock
  private JSONWriter writer;

  @Spy
  private CurrencyCodeProvider provider;

  @DisplayName("Get reader for Instant")
  @Test
  void findCurrencyReader() {
    assertNotNull(provider.findValueReader(reader, Currency.class));
  }

  @DisplayName("Get reader for String")
  @Test
  void findStringReader() {
    assertNull(provider.findValueReader(reader, String.class));
  }

  @DisplayName("Get writer for Instant")
  @Test
  void findCurrencyWriter() {
    assertNotNull(provider.findValueWriter(writer, Currency.class));
  }

  @DisplayName("Get writer for String")
  @Test
  void findStringWriter() {
    assertNull(provider.findValueWriter(writer, String.class));
  }

  @DisplayName("Read timestamp")
  @Test
  public void readHryvnia() throws IOException {
    // given
    when(parser.getIntValue()).thenReturn(980);

    // when
    var value = provider.findValueReader(reader, Currency.class).read(reader, parser);

    // then
    assertEquals(Currency.UAH, value);
  }

  @DisplayName("Write timestamp")
  @Test
  public void writeCanadianDollar() throws IOException {
    // when
    provider.findValueWriter(writer, Currency.class)
        .writeValue(writer, generator, Currency.CAD);

    // then
    verify(writer).writeValue(124);
  }

}
