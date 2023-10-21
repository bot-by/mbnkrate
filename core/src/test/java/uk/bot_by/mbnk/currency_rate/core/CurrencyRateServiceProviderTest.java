package uk.bot_by.mbnk.currency_rate.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import feign.Client;
import feign.RequestLine;
import feign.codec.Decoder;
import feign.http2client.Http2Client;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tag("fast")
class CurrencyRateServiceProviderTest {

  @Mock
  private Client client;
  @Mock
  private Decoder decoder;
  @Spy
  private CurrencyRateServiceProvider provider;

  private Class<? extends CurrencyRateService> serviceType;

  @BeforeEach
  void setUp() {
    serviceType = TestCurrencyRateService.class;
  }

  @DisplayName("Get a client")
  @Test
  void client() {
    // when and then
    assertThat(provider.getClient(), instanceOf(Http2Client.class));
  }

  @DisplayName("Get a service")
  @Test
  void service() {
    // given
    doReturn(client).when(provider).getClient();
    doReturn(decoder).when(provider).getDecoder();
    doReturn(serviceType).when(provider).getServiceType();

    // when
    var service = provider.getService();

    // then
    verify(provider).getClient();
    verify(provider).getDecoder();
    verify(provider).getServiceType();

    assertNotNull(service, "currency rate service");
  }

  interface TestCurrencyRateService extends CurrencyRateService {

    @Override
    @RequestLine(GET_CURRENCY_RATES)
    List<? extends CurrencyRate> getCurrencyRates();

  }

}