package eu.sanjin.kurelic.paintingsgarage.cart.service;

import eu.sanjin.kurelic.paintingsgarage.cart.model.CartItem;
import eu.sanjin.kurelic.paintingsgarage.cart.model.PhotoType;
import eu.sanjin.kurelic.paintingsgarage.photo.model.PhotoData;
import eu.sanjin.kurelic.paintingsgarage.photo.service.PhotoService;
import eu.sanjin.kurelic.paintingsgarage.security.model.UserDetailsModel;
import eu.sanjin.kurelic.paintingsgarage.testutil.type.UnitTest;
import eu.sanjin.kurelic.paintingsgarage.user.entity.User;
import eu.sanjin.kurelic.paintingsgarage.user.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@UnitTest
class CartServiceUnitTest {

  @Mock
  private PhotoService photoService;

  @Mock
  private UserService userService;

  @InjectMocks
  private CartService cartService;

  @BeforeAll
  static void init() {
    var securityContext = mock(SecurityContext.class);
    var authentication = mock(Authentication.class);
    var userDetailsModel = mock(UserDetailsModel.class);

    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(userDetailsModel);
    when(userDetailsModel.getId()).thenReturn(99L);

    SecurityContextHolder.setContext(securityContext);
  }

  @Test
  void checkout() {
    // Given
    var itemId = 12L;
    var item = new CartItem(itemId, PhotoType.DIGITAL);
    var items = List.of(item);

    when(userService.getUser(99L)).thenReturn(mock(User.class));
    when(photoService.buyPhoto(eq(itemId), any())).thenReturn(mock(PhotoData.class));

    // When
    var result = cartService.checkout(items);

    // Then
    assertThat(result).hasSize(1);
    verify(userService, times(1)).getUser(99L);
    verify(photoService, times(1)).buyPhoto(eq(itemId), any());
  }
}