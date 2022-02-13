package eu.sanjin.kurelic.paintingsgarage.user.repository;

import eu.sanjin.kurelic.paintingsgarage.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  List<User> findByNameStartingWith(String name);

  List<User> getAllByActiveIsTrue();

  Optional<User> getByEmail(String email);

  Optional<User> getByName(String name);
}
