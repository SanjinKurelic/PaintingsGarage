package eu.sanjin.kurelic.photostorage.security.service;

import eu.sanjin.kurelic.photostorage.user.service.UserService;
import eu.sanjin.kurelic.photostorage.security.mapper.SecurityUserModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private static final String USERNAME_NOT_FOUND = "exception.username.not.found";

  private final SecurityUserModelMapper mapper;
  private final UserService userService;
  private final MessageSource messageSource;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    var user = userService.getUser(email);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException(
        messageSource.getMessage(USERNAME_NOT_FOUND, new Object[]{email}, LocaleContextHolder.getLocale())
      );
    }
    return mapper.mapUserToUserDetails(user.get());
  }
}
