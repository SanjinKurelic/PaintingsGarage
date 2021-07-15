package eu.sanjin.kurelic.photostorage.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "smi.file")
public class FileStorageConfig {

  private String directory;

  private String thumbnailPrefix;

  private List<String> extensionList;

}
