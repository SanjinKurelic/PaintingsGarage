package eu.sanjin.kurelic.react.testutil;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SeleniumVisibilityUtil {

  private static final long DEFAULT_WAITING_TIME = 5;

  public static void waitForElementIdentifiedBy(WebDriver driver, By identifier) {
    waitForElementIdentifiedBy(driver, identifier, DEFAULT_WAITING_TIME);
  }

  public static void waitForElementIdentifiedBy(WebDriver driver, By identifier, long seconds) {
    new WebDriverWait(driver, seconds).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(identifier));
  }
}
