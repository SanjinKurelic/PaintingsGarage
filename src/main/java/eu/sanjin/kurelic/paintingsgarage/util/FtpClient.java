package eu.sanjin.kurelic.paintingsgarage.util;

import eu.sanjin.kurelic.paintingsgarage.photo.config.FileStorageConfig;
import lombok.RequiredArgsConstructor;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FtpClient {

  private final FileStorageConfig config;

  public SFTPClient getSftpClient(SSHClient client) throws IOException {
    client.addHostKeyVerifier(new PromiscuousVerifier());
    client.connect(config.getHost(), config.getPort());
    client.authPassword(config.getUsername(), config.getPassword());

    return client.newSFTPClient();
  }
}
