package eu.sanjin.kurelic.paintingsgarage.testutil.containers;

import eu.sanjin.kurelic.paintingsgarage.testutil.containers.ftp.FtpTestContainer;
import eu.sanjin.kurelic.paintingsgarage.testutil.type.IntegrationTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@IntegrationTest
@AutoConfigureMockMvc
public class IntegrationContainerTestBase {

  @Container
  @SuppressWarnings("unused")
  protected static final FtpTestContainer ftpContainer = FtpTestContainer.getInstance();
}
