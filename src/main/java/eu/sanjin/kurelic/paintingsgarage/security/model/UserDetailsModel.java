package eu.sanjin.kurelic.paintingsgarage.security.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class UserDetailsModel implements UserDetails {

  private Long id;
  private String name;
  private String email;
  private String password;
  private boolean enabled;
  private Collection<? extends GrantedAuthority> authorities;

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return isEnabled();
  }

  @Override
  public boolean isAccountNonLocked() {
    return isEnabled();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return isEnabled();
  }
}
