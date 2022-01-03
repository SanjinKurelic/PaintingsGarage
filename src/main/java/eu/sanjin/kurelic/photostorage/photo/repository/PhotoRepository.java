package eu.sanjin.kurelic.photostorage.photo.repository;

import eu.sanjin.kurelic.photostorage.photo.entity.Photo;
import eu.sanjin.kurelic.photostorage.photo.model.PhotoSize;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static eu.sanjin.kurelic.photostorage.photo.repository.PhotoSpecification.combineSpecifications;
import static eu.sanjin.kurelic.photostorage.photo.repository.PhotoSpecification.findAllByAuthorIdIn;
import static eu.sanjin.kurelic.photostorage.photo.repository.PhotoSpecification.findAllByHashtagsIdIn;
import static eu.sanjin.kurelic.photostorage.photo.repository.PhotoSpecification.findAllBySize;
import static eu.sanjin.kurelic.photostorage.photo.repository.PhotoSpecification.findAllByUploadedAfterAndUploadedBefore;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long>, JpaSpecificationExecutor<Photo> {

  List<Photo> findFirst9ByOrderByUploadedDesc();

  default List<Photo> findAllBy(List<Long> authors, List<Long> hashtags, PhotoSize size, LocalDateTime dateFrom, LocalDateTime dateTo) {
    var specificationList = new ArrayList<Specification<Photo>>();

    if (Objects.nonNull(authors) && !authors.isEmpty()) {
      specificationList.add(findAllByAuthorIdIn(authors));
    }
    if (Objects.nonNull(hashtags) && !hashtags.isEmpty()) {
      specificationList.add(findAllByHashtagsIdIn(hashtags));
    }
    if (Objects.nonNull(size)) {
      specificationList.add(findAllBySize(size));
    }
    if (Objects.nonNull(dateFrom) && Objects.nonNull(dateTo)) {
      specificationList.add(findAllByUploadedAfterAndUploadedBefore(dateFrom, dateTo));
    }

    var specification = combineSpecifications(specificationList);
    if (Objects.isNull(specification)) {
      return List.of();
    }

    return findAll(specification);
  }
}
