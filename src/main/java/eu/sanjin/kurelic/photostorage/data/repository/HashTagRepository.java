package eu.sanjin.kurelic.photostorage.data.repository;

import eu.sanjin.kurelic.photostorage.data.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag, Long> {

  List<HashTag> findAllByNameLike(String name);

}
