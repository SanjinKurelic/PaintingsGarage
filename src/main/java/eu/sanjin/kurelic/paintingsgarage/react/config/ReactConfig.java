package eu.sanjin.kurelic.paintingsgarage.react.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.react")
public class ReactConfig {

  private List<String> urls;
}
