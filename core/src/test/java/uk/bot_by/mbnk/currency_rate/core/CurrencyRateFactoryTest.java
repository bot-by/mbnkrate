package uk.bot_by.mbnk.currency_rate.core;

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
  private CurrencyRateStore currencyRateStore;
  @Mock
  private CurrencyRateServiceProvider currencyRateServiceProvider;
  @Mock
  private ServiceLoader<CurrencyRateServiceProvider> serviceProviderLoader;
  @Mock
  private ServiceLoader<CurrencyRateStore> storeLoader;

  @DisplayName("Service provider is absent")
  @Test
  void serviceProviderIsNotFound() {
    // when
    var exception = assertThrows(NoSuchElementException.class,
        CurrencyRateFactory::getServiceProvider);

    // then
    assertEquals("No Monobank Currency API providers found", exception.getMessage());
  }

  @DisplayName("Service provider is found")
  @SuppressWarnings("rawtypes")
  @Test
  void serviceProviderIsFound() {
    // given
    when(serviceProviderLoader.findFirst()).thenReturn(Optional.of(currencyRateServiceProvider));

    try (MockedStatic<ServiceLoader> staticUtilities = Mockito.mockStatic(ServiceLoader.class)) {
      staticUtilities.when(() -> ServiceLoader.load(CurrencyRateServiceProvider.class))
          .thenReturn(serviceProviderLoader);

      // when
      var instance = assertDoesNotThrow(CurrencyRateFactory::getServiceProvider);

      // then
      assertEquals(currencyRateServiceProvider, instance);
    }
  }

  @DisplayName("Store provider is absent")
  @Test
  void storeProviderIsNotFound() {
    // when
    var exception = assertThrows(NoSuchElementException.class, CurrencyRateFactory::getStore);

    // then
    assertEquals("No Currency Rate Store providers found", exception.getMessage());
  }

  @DisplayName("Service provider is found")
  @SuppressWarnings("rawtypes")
  @Test
  void storeProviderIsFound() {
    // given
    when(storeLoader.findFirst()).thenReturn(Optional.of(currencyRateStore));

    try (MockedStatic<ServiceLoader> staticUtilities = Mockito.mockStatic(ServiceLoader.class)) {
      staticUtilities.when(() -> ServiceLoader.load(CurrencyRateStore.class))
          .thenReturn(storeLoader);

      // when
      var instance = assertDoesNotThrow(CurrencyRateFactory::getStore);

      // then
      assertEquals(currencyRateStore, instance);
    }
  }

}