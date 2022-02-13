package eu.sanjin.kurelic.paintingsgarage.react.config;

import eu.sanjin.kurelic.paintingsgarage.react.filter.ReactRouterFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ReactFilterConfig {

  private final ReactConfig config;

  @Bean
  public FilterRegistrationBean<ReactRouterFilter> reactRouterFilter() {
    var filterBean = new FilterRegistrationBean<ReactRouterFilter>();

    filterBean.setFilter(new ReactRouterFilter());
    filterBean.setUrlPatterns(config.getUrls());

    return filterBean;
  }
}
