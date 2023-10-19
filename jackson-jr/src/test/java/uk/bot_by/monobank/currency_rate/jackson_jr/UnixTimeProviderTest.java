package uk.bot_by.monobank.currency_rate.jackson_jr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.jr.ob.impl.JSONReader;
import com.fasterxml.jackson.jr.ob.impl.JSONWriter;
import java.io.IOException;
import java.time.Instant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tag("fast")
class UnixTimeProviderTest {

  @Mock
  private JsonGenerator generator;
  @Mock
  private JsonParser parser;
  @Mock
  private JSONReader reader;
  @Mock
  private JSONWriter writer;

  @Spy
  private UnixTimeProvider provider;

  @DisplayName("Get reader for Instant")
  @Test
  void findInstantReader() {
    assertNotNull(provider.findValueReader(reader, Instant.class));
  }

  @DisplayName("Get reader for String")
  @Test
  void findStringReader() {
    assertNull(provider.findValueReader(reader, String.class));
  }

  @DisplayName("Get writer for Instant")
  @Test
  void findInstantWriter() {
    assertNotNull(provider.findValueWriter(writer, Instant.class));
  }

  @DisplayName("Get writer for String")
  @Test
  void findStringWriter() {
    assertNull(provider.findValueWriter(writer, String.class));
  }

  @DisplayName("Read timestamp")
  @Test
  public void readUnixTime() throws IOException {
    // given
    when(parser.getLongValue()).thenReturn(123456789L);

    // when
    Object value = provider.findValueReader(reader, Instant.class).read(reader, parser);

    // then
    assertEquals(123456789L, ((Instant) value).getEpochSecond());
  }

  @DisplayName("Write timestamp")
  @Test
  public void writeUnixTime() throws IOException {
    // when
    provider.findValueWriter(writer, Instant.class)
        .writeValue(writer, generator, Instant.ofEpochSecond(123456789L));

    // then
    verify(writer).writeValue(eq(123456789L));
  }

}
