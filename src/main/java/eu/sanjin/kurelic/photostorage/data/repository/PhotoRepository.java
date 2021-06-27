package eu.sanjin.kurelic.photostorage.data.repository;

import eu.sanjin.kurelic.photostorage.data.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

  List<Photo> findFirst10ByOrderByUploadedDesc();

}
