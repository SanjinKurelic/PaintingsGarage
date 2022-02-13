package eu.sanjin.kurelic.paintingsgarage.security.model;

public record LoginUserRequest(
  String email,
  String password
) {
}
