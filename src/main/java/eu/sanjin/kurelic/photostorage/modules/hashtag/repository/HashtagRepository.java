package eu.sanjin.kurelic.photostorage.modules.hashtag.repository;

import eu.sanjin.kurelic.photostorage.modules.hashtag.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

  List<Hashtag> findByNameStartingWith(String name);

}
