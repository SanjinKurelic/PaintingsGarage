package eu.sanjin.kurelic.photostorage.cart.service;

import eu.sanjin.kurelic.photostorage.cart.model.CartItem;
import eu.sanjin.kurelic.photostorage.photo.model.PhotoData;
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

  public List<PhotoData> checkout(List<CartItem> items) {
    var userDetailsModel = (UserDetailsModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    var user = userService.getUser(userDetailsModel.getId());

    return items.stream().map(cartItem -> photoService.buyPhoto(cartItem.photoId(), user)).toList();
  }
}
