package eu.sanjin.kurelic.photostorage.security.model;

public record JwtResponse(
  String token,
  Long id,
  String email,
  String name,
  String role
) {
}
