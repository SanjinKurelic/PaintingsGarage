package eu.sanjin.kurelic.photostorage.security.configuration;

import eu.sanjin.kurelic.photostorage.modules.user.entity.UserRole;
import eu.sanjin.kurelic.photostorage.security.filter.AuthTokenFilter;
import eu.sanjin.kurelic.photostorage.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsServiceImpl userDetailsService;
  private final AuthTokenFilter authTokenFilter;

  @Bean
  public DaoAuthenticationProvider authProvider() {
    var authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    return authProvider;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .cors().and().csrf().disable()
      //.exceptionHandling().authenticationEntryPoint(AuthEntryPointJwt).and()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
      .authorizeRequests()
      // Auth
      .antMatchers("/api/auth/**").permitAll()
      // Hashtag
      .antMatchers("/api/hashtag/**").permitAll()
      // Photo
      .antMatchers(HttpMethod.GET, "/api/photo/**").permitAll()
      .antMatchers(HttpMethod.POST, "/api/photo/**").hasAnyRole(
        UserRole.ROLE_USER.getApplicationRole(), UserRole.ROLE_ADMIN.getApplicationRole()
      )
      // User
      .antMatchers("/api/user/**").permitAll()
      // Admin
      .antMatchers("/api/admin/**").hasRole(UserRole.ROLE_ADMIN.getApplicationRole())
      .anyRequest().permitAll();

    http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(authProvider());
  }

  @Bean
  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }
}
