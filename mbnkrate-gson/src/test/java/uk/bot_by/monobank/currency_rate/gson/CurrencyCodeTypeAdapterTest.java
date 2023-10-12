package uk.bot_by.monobank.currency_rate.gson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
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
class CurrencyCodeTypeAdapterTest {

  @Mock
  private JsonReader reader;
  @Mock
  private JsonWriter writer;

  private CurrencyCodeTypeAdapter typeAdapter;

  @BeforeEach
  void setUp() {
    typeAdapter = new CurrencyCodeTypeAdapter();
  }

  @DisplayName("Read numeric code")
  @Test
  void readHryvnia() throws IOException {
    // given
    when(reader.peek()).thenReturn(JsonToken.NUMBER);
    when(reader.nextInt()).thenReturn(980);

    // when
    var currency = typeAdapter.read(reader);

    // then
    verify(reader).peek();
    verify(reader).nextInt();

    assertEquals(Currency.UAH, currency);
  }

  @DisplayName("Read null value")
  @Test
  void readNullValue() throws IOException {
    // given
    when(reader.peek()).thenReturn(JsonToken.NULL);

    // when
    var nullValue = typeAdapter.read(reader);

    // then
    verify(reader).peek();
    verify(reader, never()).nextInt();
    verify(reader).nextNull();

    assertNull(nullValue);
  }

  @DisplayName("Write numeric code")
  @Test
  void writeCanadianDollar() throws IOException {
    // when
    typeAdapter.write(writer, Currency.CAD);

    // then
    verify(writer).value(Currency.CAD.getNumericCode());
  }

  @DisplayName("Write null value")
  @Test
  void writeNullValue() throws IOException {
    // when
    typeAdapter.write(writer, null);

    // then
    verify(writer, never()).value(anyInt());
    verify(writer).nullValue();
  }
}