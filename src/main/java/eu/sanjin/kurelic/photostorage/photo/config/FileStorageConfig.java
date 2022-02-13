package eu.sanjin.kurelic.photostorage.photo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.file")
public class FileStorageConfig {

  private String directory;

  private String host;

  private Integer port;

  private String username;

  private String password;

  private List<String> extensionList;
}
