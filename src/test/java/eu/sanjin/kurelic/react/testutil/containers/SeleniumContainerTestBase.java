package eu.sanjin.kurelic.react.testutil.containers;

import eu.sanjin.kurelic.react.testutil.containers.selenium.SeleniumTestContainer;
import eu.sanjin.kurelic.react.testutil.type.SeleniumTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SeleniumTest
@Testcontainers
public abstract class SeleniumContainerTestBase {

  @Container
  protected static final SeleniumTestContainer seleniumContainer = SeleniumTestContainer.getInstance();
}
