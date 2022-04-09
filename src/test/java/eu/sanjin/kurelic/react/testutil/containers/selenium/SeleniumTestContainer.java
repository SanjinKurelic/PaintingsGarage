package eu.sanjin.kurelic.react.testutil.containers.selenium;

import org.openqa.selenium.firefox.FirefoxOptions;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.BrowserWebDriverContainer;

public class SeleniumTestContainer extends BrowserWebDriverContainer<SeleniumTestContainer> {

  private static final Integer HOST_PORT = 8080;
  private static final String TEST_CONTAINER_URL = String.format("http://host.testcontainers.internal:%d/", HOST_PORT);

  private static volatile SeleniumTestContainer container;

  private SeleniumTestContainer() {
    withCapabilities(new FirefoxOptions());
    Testcontainers.exposeHostPorts(HOST_PORT);
  }

  public static SeleniumTestContainer getInstance() {
    if (container == null) {
      synchronized (SeleniumTestContainer.class) {
        if (container == null) {
          container = new SeleniumTestContainer();
        }
      }
    }

    return container;
  }

  @Override
  public void stop() {
    // Do nothing, let JVM handle stop
  }

  public void fetchPage(String page) {
    getWebDriver().get(String.format("%s%s", TEST_CONTAINER_URL, page));
  }

  public void fetchRootPage() {
    fetchPage("");
  }
}
