package eu.sanjin.kurelic.photostorage.hashtag.repository;

import eu.sanjin.kurelic.photostorage.hashtag.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

  List<Hashtag> findByNameStartingWith(String name);

  Optional<Hashtag> getByName(String name);
}
