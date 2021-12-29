package eu.sanjin.kurelic.photostorage.security.model;

public record LoginUserRequest(
  String email,
  String password
) {
}
