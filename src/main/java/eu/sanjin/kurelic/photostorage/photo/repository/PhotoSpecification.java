package eu.sanjin.kurelic.photostorage.photo.repository;

import eu.sanjin.kurelic.photostorage.photo.entity.Photo;
import eu.sanjin.kurelic.photostorage.photo.entity.Photo_;
import eu.sanjin.kurelic.photostorage.photo.model.PhotoSize;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PhotoSpecification {

  public static Specification<Photo> findAllByAuthorIdIn(List<Long> authorIds) {
    return (root, criteriaQuery, criteriaBuilder) -> root.get(Photo_.author).in(authorIds);
  }

  public static Specification<Photo> findAllByPhotoIdIn(List<Long> photoIds) {
    return (root, criteriaQuery, criteriaBuilder) -> root.get(Photo_.id).in(photoIds);
  }

  public static Specification<Photo> findAllBySize(PhotoSize size) {
    return (root, criteriaQuery, criteriaBuilder) -> switch (size) {
      case SMALL -> criteriaBuilder.le(root.get(Photo_.size), 1000);
      case BIG -> criteriaBuilder.ge(root.get(Photo_.size), 4000);
    };
  }

  public static Specification<Photo> findAllByUploadedAfterAndUploadedBefore(LocalDateTime from, LocalDateTime to) {
    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
      criteriaBuilder.greaterThanOrEqualTo(root.get(Photo_.uploaded), from),
      criteriaBuilder.lessThanOrEqualTo(root.get(Photo_.uploaded), to)
    );
  }

  public static Specification<Photo> combineSpecifications(List<Specification<Photo>> specificationList) {
    Specification<Photo> specification = null;
    for (var s : specificationList) {
      if (Objects.isNull(specification)) {
        specification = s;
      } else {
        specification = s.and(specification);
      }
    }

    return specification;
  }

}
