package eu.sanjin.kurelic.paintingsgarage.security.controller;

import eu.sanjin.kurelic.paintingsgarage.audit.aspect.LogUserLogin;
import eu.sanjin.kurelic.paintingsgarage.audit.aspect.LogUserRegistration;
import eu.sanjin.kurelic.paintingsgarage.security.model.JwtResponse;
import eu.sanjin.kurelic.paintingsgarage.security.model.LoginUserRequest;
import eu.sanjin.kurelic.paintingsgarage.security.model.RegisterUserRequest;
import eu.sanjin.kurelic.paintingsgarage.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  @LogUserLogin
  @PostMapping("/loginUser")
  public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginUserRequest loginRequest) {
    return ResponseEntity.ok(authService.authenticateUser(loginRequest));
  }

  @LogUserRegistration
  @PostMapping("/registerUser")
  public ResponseEntity<JwtResponse> registerUser(@RequestBody RegisterUserRequest registerRequest) {
    if (!authService.registerUser(registerRequest)) {
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(authService.authenticateUser(
      new LoginUserRequest(registerRequest.email(), registerRequest.password()))
    );
  }
}
