package eu.sanjin.kurelic.photostorage.security.model;

public record RegisterUserRequest(
  String name,
  String email,
  String password,
  Integer plan
) {
}
