package eu.sanjin.kurelic.paintingsgarage.security.configuration;

import eu.sanjin.kurelic.paintingsgarage.security.filter.AuthTokenFilter;
import eu.sanjin.kurelic.paintingsgarage.security.service.UserDetailsServiceImpl;
import eu.sanjin.kurelic.paintingsgarage.user.entity.UserRole;
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
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
      .authorizeRequests()
      // Audit
      .antMatchers("/api/audit/**").hasRole(UserRole.ROLE_ADMIN.getApplicationRole())
      // Auth
      .antMatchers("/api/auth/**").permitAll()
      // Cart
      .antMatchers("/api/cart").hasRole(UserRole.ROLE_USER.getApplicationRole())
      // Hashtag
      .antMatchers("/api/search/**").permitAll()
      // Photo
      .antMatchers(HttpMethod.GET, "/api/photo/**").permitAll()
      .antMatchers("/api/photo/**").hasAnyRole(
        UserRole.ROLE_USER.getApplicationRole(), UserRole.ROLE_ADMIN.getApplicationRole()
      )
      // User
      .antMatchers(HttpMethod.PUT,"/api/user/**").hasAnyRole(
        UserRole.ROLE_USER.getApplicationRole(), UserRole.ROLE_ADMIN.getApplicationRole()
      )
      .antMatchers(HttpMethod.GET,"/api/user/current").hasAnyRole(
        UserRole.ROLE_USER.getApplicationRole(), UserRole.ROLE_ADMIN.getApplicationRole()
      )
      .antMatchers("/api/user").hasRole(UserRole.ROLE_ADMIN.getApplicationRole())
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
