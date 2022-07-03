package eu.sanjin.kurelic.react.login;

import eu.sanjin.kurelic.react.testutil.containers.SeleniumContainerTestBase;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginPageTest extends SeleniumContainerTestBase {

  @Test
  void shouldRedirectUserToLoginPage() {
    // When
    seleniumContainer.fetchPage("user");

    // Then
    assertTrue(seleniumContainer.getWebDriver().getCurrentUrl().endsWith("login"));
  }

  @Test
  void shouldAlertUserForIncorrectLoginData() {
    // Given
    seleniumContainer.fetchPage("login");

    var driver = seleniumContainer.getWebDriver();
    var nameInput = driver.findElement(By.name("email"));
    var passwordInput = driver.findElement(By.name("password"));

    // When
    nameInput.sendKeys("abc@example.com");
    passwordInput.sendKeys("password.123");
    passwordInput.sendKeys(Keys.ENTER);

    // Then
    assertThat(driver.findElement(By.className("alert-danger")).getText()).isEqualTo("Invalid email or password");
  }
}
