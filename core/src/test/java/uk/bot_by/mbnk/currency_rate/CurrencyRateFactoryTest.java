package uk.bot_by.mbnk.currency_rate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ServiceLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tag("slow")
class CurrencyRateFactoryTest {

  @Mock
  private CurrencyRateServiceProvider currencyRateServiceProvider;
  @Mock
  private ServiceLoader<CurrencyRateServiceProvider> serviceLoader;


  @DisplayName("Provider is absent")
  @Test
  void providerIsNotFound() {
    // when
    var exception = assertThrows(NoSuchElementException.class,
        CurrencyRateFactory::getServiceProvider);

    // then
    assertEquals("No Monobank Currency API providers found", exception.getMessage());
  }

  @DisplayName("Provider is found")
  @SuppressWarnings("rawtypes")
  @Test
  void providerIsFound() {
    // given
    when(serviceLoader.findFirst()).thenReturn(Optional.of(currencyRateServiceProvider));

    try (MockedStatic<ServiceLoader> staticUtilities = Mockito.mockStatic(ServiceLoader.class)) {
      staticUtilities.when(() -> ServiceLoader.load(CurrencyRateServiceProvider.class))
          .thenReturn(serviceLoader);

      // when
      var instance = assertDoesNotThrow(CurrencyRateFactory::getServiceProvider);

      // then
      assertEquals(currencyRateServiceProvider, instance);
    }
  }

}