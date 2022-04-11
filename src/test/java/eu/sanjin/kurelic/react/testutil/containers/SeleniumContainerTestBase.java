package eu.sanjin.kurelic.react.testutil.containers;

import eu.sanjin.kurelic.paintingsgarage.PaintingsGarageApplication;
import eu.sanjin.kurelic.react.testutil.containers.selenium.SeleniumTestContainer;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(classes = PaintingsGarageApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class SeleniumContainerTestBase {

  @Container
  protected static final SeleniumTestContainer seleniumContainer = SeleniumTestContainer.getInstance();

}
