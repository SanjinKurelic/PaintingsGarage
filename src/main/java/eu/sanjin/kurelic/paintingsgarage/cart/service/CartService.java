package eu.sanjin.kurelic.paintingsgarage.cart.service;

import eu.sanjin.kurelic.paintingsgarage.cart.model.CartItem;
import eu.sanjin.kurelic.paintingsgarage.photo.model.PhotoData;
import eu.sanjin.kurelic.paintingsgarage.photo.service.PhotoService;
import eu.sanjin.kurelic.paintingsgarage.security.model.UserDetailsModel;
import eu.sanjin.kurelic.paintingsgarage.user.service.UserService;
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
