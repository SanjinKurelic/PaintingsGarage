package eu.sanjin.kurelic.react;

import eu.sanjin.kurelic.react.testutil.containers.SeleniumContainerTestBase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WebRootTest extends SeleniumContainerTestBase {

  @Test
  void shouldDisplayWebsiteTitle() {
    seleniumContainer.fetchRootPage();

    var messageElement = seleniumContainer.getWebDriver().getTitle();

    assertEquals("Paintings Garage", messageElement);
  }
}
