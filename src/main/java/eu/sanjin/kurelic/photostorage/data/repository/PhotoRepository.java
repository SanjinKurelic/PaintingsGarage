package eu.sanjin.kurelic.photostorage.data.repository;

import eu.sanjin.kurelic.photostorage.data.entity.Photo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static eu.sanjin.kurelic.photostorage.data.repository.PhotoSpecification.combineSpecifications;
import static eu.sanjin.kurelic.photostorage.data.repository.PhotoSpecification.findAllByAuthorIdIn;
import static eu.sanjin.kurelic.photostorage.data.repository.PhotoSpecification.findAllByHashTagsIdIn;
import static eu.sanjin.kurelic.photostorage.data.repository.PhotoSpecification.findAllBySize;
import static eu.sanjin.kurelic.photostorage.data.repository.PhotoSpecification.findAllByUploadedAfterAndUploadedBefore;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long>, JpaSpecificationExecutor<Photo> {

  List<Photo> findFirst10ByOrderByUploadedDesc();

  default List<Photo> findAllBy(List<Long> authorIds, List<Long> hashTagIds, Integer size, LocalDateTime from, LocalDateTime to) {
    var specificationList = new ArrayList<Specification<Photo>>();

    if (!authorIds.isEmpty()) {
      specificationList.add(findAllByAuthorIdIn(authorIds));
    }
    if (!hashTagIds.isEmpty()) {
      specificationList.add(findAllByHashTagsIdIn(hashTagIds));
    }
    if (Objects.nonNull(size)) {
      specificationList.add(findAllBySize(size));
    }
    if (Objects.nonNull(from) && Objects.nonNull(to)) {
      specificationList.add(findAllByUploadedAfterAndUploadedBefore(from, to));
    }

    var specification = combineSpecifications(specificationList);
    if (Objects.isNull(specification)) {
      return List.of();
    }

    return findAll(specification);
  }

}
