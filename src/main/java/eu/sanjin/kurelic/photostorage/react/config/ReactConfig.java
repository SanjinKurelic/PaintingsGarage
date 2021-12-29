package eu.sanjin.kurelic.photostorage.react.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.react")
public class ReactConfig {

  private List<String> urls;
}
