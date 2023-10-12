package uk.bot_by.monobank.currency_rate.gson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tag("fast")
public class UnixTimeTypeAdapterTest {

  @Mock
  private JsonReader reader;
  @Mock
  private JsonWriter writer;

  private UnixTimeTypeAdapter typeAdapter;

  @BeforeEach
  public void setUp() {
    typeAdapter = new UnixTimeTypeAdapter();
  }

  @DisplayName("Read timestamp")
  @Test
  public void readTimestamp() throws IOException {
    // given
    when(reader.peek()).thenReturn(JsonToken.NUMBER);
    when(reader.nextLong()).thenReturn(1628370606L);

    // when
    Instant timestamp = typeAdapter.read(reader);

    // then
    verify(reader).peek();
    verify(reader).nextLong();

    assertEquals(1628370606, timestamp.getEpochSecond(), "timestamp");
  }

  @DisplayName("Read null value")
  @Test
  public void readNullValue() throws IOException {
    // given
    when(reader.peek()).thenReturn(JsonToken.NULL);

    // when
    Instant timestamp = typeAdapter.read(reader);

    // then
    verify(reader).peek();
    verify(reader, never()).nextLong();
    verify(reader).nextNull();

    assertNull(timestamp, "timestamp");
  }

  @DisplayName("Write timestamp")
  @Test
  public void writeTimestamp() throws IOException {
    // given
    Instant timestamp = Instant.ofEpochSecond(1628370606);

    // when
    typeAdapter.write(writer, timestamp);

    // then
    verify(writer).value(1628370606);
  }

  @DisplayName("Write null value")
  @Test
  public void writeNullValue() throws IOException {
    // when
    typeAdapter.write(writer, null);

    // then
    verify(writer, never()).value(anyLong());
    verify(writer).nullValue();
  }

}
