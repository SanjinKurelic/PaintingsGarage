package eu.sanjin.kurelic.react.cart;

import eu.sanjin.kurelic.react.testutil.containers.SeleniumContainerTestBase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CartPageTest extends SeleniumContainerTestBase {

  @Test
  void shouldDisplayEmptyCartWarning() {
    // When
    seleniumContainer.fetchPage("cart");

    // Then
    var warningMessage = seleniumContainer.getWebDriver().findElementByClassName("alert-warning");
    assertThat(warningMessage.getText()).isEqualTo("Your cart is empty");
  }
}
