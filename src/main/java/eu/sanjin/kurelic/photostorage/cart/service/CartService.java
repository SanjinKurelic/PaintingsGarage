package eu.sanjin.kurelic.photostorage.cart.service;

import eu.sanjin.kurelic.photostorage.cart.model.CartItem;
import eu.sanjin.kurelic.photostorage.common.exceptions.InternalServerError;
import eu.sanjin.kurelic.photostorage.photo.service.PhotoService;
import eu.sanjin.kurelic.photostorage.security.model.UserDetailsModel;
import eu.sanjin.kurelic.photostorage.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

  private final PhotoService photoService;
  private final UserService userService;

  public void checkout(List<CartItem> items) {
    var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (!(principal instanceof UserDetailsModel userDetailsModel)) {
      throw new InternalServerError("User not registered");
    }

    var user = userService.getUser(userDetailsModel.getId());
    items.forEach(cartItem -> photoService.buyPhoto(cartItem.photoId(), user));
  }
}
