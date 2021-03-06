package eu.sanjin.kurelic.paintingsgarage.user.model;

import java.time.LocalDate;

public record Author(
  Long id,
  String name,
  String email,
  LocalDate registered,
  String avatar,
  String plan
) {
}
