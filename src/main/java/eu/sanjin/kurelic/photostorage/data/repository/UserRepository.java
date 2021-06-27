package eu.sanjin.kurelic.photostorage.data.repository;

import eu.sanjin.kurelic.photostorage.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  List<User> findAllByUsernameLike(String username);

}
