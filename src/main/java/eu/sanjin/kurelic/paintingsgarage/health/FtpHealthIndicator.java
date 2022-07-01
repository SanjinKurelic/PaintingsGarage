package eu.sanjin.kurelic.paintingsgarage.health;

import eu.sanjin.kurelic.paintingsgarage.util.FtpClient;
import lombok.RequiredArgsConstructor;
import net.schmizz.sshj.SSHClient;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FtpHealthIndicator implements HealthIndicator {

  private final FtpClient ftpClient;

  @Override
  public Health health() {
    try (var ignore = ftpClient.getSftpClient(new SSHClient())) {
      return Health.up().build();
    } catch (Exception e) {
      return Health.down().build();
    }
  }
}
