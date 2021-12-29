package eu.sanjin.kurelic.photostorage;

import eu.sanjin.kurelic.photostorage.react.config.ReactConfig;
import eu.sanjin.kurelic.photostorage.photo.config.FileStorageConfig;
import eu.sanjin.kurelic.photostorage.security.configuration.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageConfig.class, JwtConfig.class, ReactConfig.class})
public class PhotoStorageApplication {

  public static void main(String[] args) {
    SpringApplication.run(PhotoStorageApplication.class, args);
  }
}
