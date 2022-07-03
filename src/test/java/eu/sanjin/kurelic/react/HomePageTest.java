package eu.sanjin.kurelic.react;

import eu.sanjin.kurelic.react.testutil.containers.SeleniumContainerTestBase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomePageTest extends SeleniumContainerTestBase {

  @Test
  void shouldDisplayWebsiteTitle() {
    // Given
    seleniumContainer.fetchRootPage();

    // When
    var messageElement = seleniumContainer.getWebDriver().getTitle();

    // Then
    assertEquals("Paintings Garage", messageElement);
  }
}
