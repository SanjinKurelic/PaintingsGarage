package eu.sanjin.kurelic.photostorage.security.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.jwt")
public class JwtConfig {

  private String jwtSecret;

  private int jwtExpiration;
}
