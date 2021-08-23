package eu.sanjin.kurelic.photostorage.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "smp.file")
public class FileStorageConfig {

  private String directory;

  private String host;

  private Integer port;

  private String username;

  private String password;

  private List<String> extensionList;

}
