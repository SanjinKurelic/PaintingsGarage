package eu.sanjin.kurelic.paintingsgarage.security.model;

public record RegisterUserRequest(
  String name,
  String email,
  String password,
  Integer plan
) {
}
