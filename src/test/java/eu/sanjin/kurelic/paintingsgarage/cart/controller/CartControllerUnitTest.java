package eu.sanjin.kurelic.paintingsgarage.cart.controller;

import eu.sanjin.kurelic.paintingsgarage.cart.model.CartItem;
import eu.sanjin.kurelic.paintingsgarage.cart.service.CartService;
import eu.sanjin.kurelic.paintingsgarage.photo.model.PhotoData;
import eu.sanjin.kurelic.paintingsgarage.testutil.type.UnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@UnitTest
class CartControllerUnitTest {

  @Mock
  private CartService cartService;

  @InjectMocks
  private CartController cartController;

  @Test
  void shouldCheckout() {
    // Given
    var items = List.of(mock(CartItem.class));

    when(cartService.checkout(items)).thenReturn(List.of(mock(PhotoData.class)));

    // When
    var result = cartController.checkout(items);

    // Then
    assertThat(result).isInstanceOf(ResponseEntity.class);
    verify(cartService, times(1)).checkout(items);
  }
}