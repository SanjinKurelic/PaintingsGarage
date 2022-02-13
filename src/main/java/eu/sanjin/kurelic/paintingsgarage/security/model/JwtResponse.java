package eu.sanjin.kurelic.paintingsgarage.security.model;

public record JwtResponse(
  String token,
  Long id,
  String email,
  String name,
  String role
) {
}
