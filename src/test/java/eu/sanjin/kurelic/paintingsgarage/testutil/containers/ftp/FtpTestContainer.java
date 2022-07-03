package eu.sanjin.kurelic.paintingsgarage.testutil.containers.ftp;

import eu.sanjin.kurelic.react.testutil.containers.selenium.SeleniumTestContainer;
import org.testcontainers.containers.GenericContainer;

public class FtpTestContainer extends GenericContainer<FtpTestContainer> {

  private static final String SFTP_CONTAINER_IMAGE = "atmoz/sftp";
  private static final Integer SFTP_DEFAULT_PORT = 22;

  private static volatile FtpTestContainer container;

  private FtpTestContainer() {
    super(SFTP_CONTAINER_IMAGE);

    withCommand("user:password:1001");
    withExposedPorts(SFTP_DEFAULT_PORT);
  }

  public static FtpTestContainer getInstance() {
    if (container == null) {
      synchronized (SeleniumTestContainer.class) {
        if (container == null) {
          container = new FtpTestContainer();
        }
      }
    }

    return container;
  }

  @Override
  public void start() {
    super.start();

    System.setProperty("SFTP_TESTCONTAINER_HOSTNAME", container.getContainerIpAddress());
    System.setProperty("SFTP_TESTCONTAINER_PORT", String.valueOf(container.getMappedPort(SFTP_DEFAULT_PORT)));
  }

  @Override
  public void stop() {
    // Do nothing, let JVM handle stop
  }
}
