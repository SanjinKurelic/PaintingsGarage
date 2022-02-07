package eu.sanjin.kurelic.photostorage.cart.controller;

import eu.sanjin.kurelic.photostorage.audit.aspect.LogPhotoBought;
import eu.sanjin.kurelic.photostorage.cart.model.CartItem;
import eu.sanjin.kurelic.photostorage.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

  @PostMapping
  @LogPhotoBought
  public void checkout(@RequestBody List<CartItem> items) {
    cartService.checkout(items);
  }
}
