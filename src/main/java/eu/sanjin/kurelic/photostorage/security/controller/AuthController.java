package eu.sanjin.kurelic.photostorage.security.controller;

import eu.sanjin.kurelic.photostorage.modules.user.entity.UserRole;
import eu.sanjin.kurelic.photostorage.modules.user.service.UserService;
import eu.sanjin.kurelic.photostorage.security.mapper.SecurityUserModelMapper;
import eu.sanjin.kurelic.photostorage.security.model.JwtResponse;
import eu.sanjin.kurelic.photostorage.security.model.LoginUserRequest;
import eu.sanjin.kurelic.photostorage.security.model.RegisterUserRequest;
import eu.sanjin.kurelic.photostorage.security.model.UserDetailsModel;
import eu.sanjin.kurelic.photostorage.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final SecurityUserModelMapper mapper;
  private final UserService userService;
  private final JwtService jwtService;

  @PostMapping("/loginUser")
  public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginUserRequest loginRequest) {
    var authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    var jwt = jwtService.generateJwtToken(authentication);
    var userDetails = (UserDetailsModel) authentication.getPrincipal();
    var role = userDetails.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority)
      .orElse(UserRole.ROLE_USER.name());

    return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail(), userDetails.getName(), role));
  }

  @PostMapping("/registerUser")
  public ResponseEntity<JwtResponse> registerUser(@RequestBody RegisterUserRequest registerRequest) {
    if (!userService.addNewUser(mapper.mapRegisterUserToUser(registerRequest))) {
      return ResponseEntity.badRequest().build();
    }

    return authenticateUser(new LoginUserRequest(registerRequest.email(), registerRequest.password()));
  }
}
