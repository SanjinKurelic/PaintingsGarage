package eu.sanjin.kurelic.paintingsgarage.cart.controller;

import eu.sanjin.kurelic.paintingsgarage.audit.aspect.LogPhotoBought;
import eu.sanjin.kurelic.paintingsgarage.cart.model.CartItem;
import eu.sanjin.kurelic.paintingsgarage.cart.service.CartService;
import eu.sanjin.kurelic.paintingsgarage.photo.model.PhotoData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<List<PhotoData>> checkout(@RequestBody List<CartItem> items) {
    return ResponseEntity.ok(cartService.checkout(items));
  }
}
