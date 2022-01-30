package eu.sanjin.kurelic.photostorage.photo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PhotoData(
  String path,
  String thumbnail,
  String description,
  BigDecimal digitalPrice,
  BigDecimal paintingPrice,
  LocalDateTime uploaded,
  String author,
  List<String> hashtags,
  PhotoOwnershipType ownershipType
) {
}
