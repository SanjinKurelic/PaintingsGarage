package eu.sanjin.kurelic.photostorage.modules.user.model;

import java.time.LocalDate;

public record Author(
  Long id,
  String name,
  String email,
  LocalDate registered,
  String avatar
) {
}
