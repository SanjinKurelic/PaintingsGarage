package eu.sanjin.kurelic.react.search;

import eu.sanjin.kurelic.react.testutil.SeleniumVisibilityUtil;
import eu.sanjin.kurelic.react.testutil.containers.SeleniumContainerTestBase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchPageTest extends SeleniumContainerTestBase {

  @Test
  void shouldFindSomeImagesBySize() {
    // Given
    seleniumContainer.fetchRootPage();

    var driver = seleniumContainer.getWebDriver();
    var searchContainer = driver.findElement(By.className("search-input"));
    var searchActions = searchContainer.findElements(By.className("search-action"));
    var searchSize = searchActions.get(1);
    var searchAction = searchActions.get(2);

    // When
    searchSize.click();
    searchAction.click();

    // Then
    var searchResults = driver.findElement(By.className("container"));

    assertNotNull(searchResults);
    assertFalse(searchResults.findElements(By.tagName("img")).isEmpty());
  }

  @Test
  void shouldShowAlertThatNothingWasFound() {
    // Given
    seleniumContainer.fetchRootPage();

    var driver = seleniumContainer.getWebDriver();
    var searchContainer = driver.findElement(By.className("search-input"));
    var searchInput = searchContainer.findElement(By.tagName("input"));
    var searchActions = searchContainer.findElements(By.className("search-action"));
    var searchAction = searchActions.get(searchActions.size() - 1);

    // When
    searchInput.sendKeys(UUID.randomUUID().toString());

    searchAction.click();

    // Then
    var alert = driver.findElement(By.className("alert"));

    assertNotNull(alert);
    assertTrue(alert.getText().startsWith("Sorry, but nothing matched your search terms. Please try again with some different keywords or"));
  }

  @Test
  @Disabled("React wraps input in internal structure so it's not possible to run this test")
  void shouldFindSomeImagesByAuthor() {
    // Given
    seleniumContainer.fetchRootPage();

    var driver = seleniumContainer.getWebDriver();
    var searchContainer = driver.findElement(By.className("search-input"));
    var searchInput = searchContainer.findElement(By.tagName("input"));
    var searchActions = searchContainer.findElements(By.className("search-action"));
    var searchAction = searchActions.get(searchActions.size() - 1);

    // When
    searchInput.sendKeys("@pica");

    SeleniumVisibilityUtil.waitForElementIdentifiedBy(driver, By.className("search-content-result"));
    driver.findElement(By.className("search-content-result")).click();

    searchAction.click();

    // Then
    var searchResults = driver.findElement(By.className("container"));

    assertNotNull(searchResults);
    assertFalse(searchResults.findElements(By.tagName("img")).isEmpty());
  }
}
