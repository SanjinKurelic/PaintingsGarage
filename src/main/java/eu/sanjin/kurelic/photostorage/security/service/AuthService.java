package eu.sanjin.kurelic.photostorage.security.service;

import eu.sanjin.kurelic.photostorage.security.mapper.SecurityUserModelMapper;
import eu.sanjin.kurelic.photostorage.security.model.JwtResponse;
import eu.sanjin.kurelic.photostorage.security.model.LoginUserRequest;
import eu.sanjin.kurelic.photostorage.security.model.RegisterUserRequest;
import eu.sanjin.kurelic.photostorage.security.model.UserDetailsModel;
import eu.sanjin.kurelic.photostorage.user.entity.UserRole;
import eu.sanjin.kurelic.photostorage.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final SecurityUserModelMapper mapper;
  private final UserService userService;
  private final JwtService jwtService;

  public JwtResponse authenticateUser(LoginUserRequest loginRequest) {
    var authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    var jwt = jwtService.generateJwtToken(authentication);
    var userDetails = (UserDetailsModel) authentication.getPrincipal();
    var role = userDetails.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority)
      .orElse(UserRole.ROLE_USER.name());

    return new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail(), userDetails.getName(), role);
  }

  public boolean registerUser(RegisterUserRequest registerRequest) {
    return userService.addNewUser(mapper.mapRegisterUserToUser(registerRequest));
  }
}
