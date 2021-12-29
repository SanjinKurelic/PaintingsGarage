package eu.sanjin.kurelic.photostorage.user.service;

import eu.sanjin.kurelic.photostorage.common.model.SearchResult;
import eu.sanjin.kurelic.photostorage.user.entity.User;
import eu.sanjin.kurelic.photostorage.user.mapper.UserMapper;
import eu.sanjin.kurelic.photostorage.user.model.Author;
import eu.sanjin.kurelic.photostorage.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;
  private final UserMapper mapper;

  public List<SearchResult> findAuthor(String name) {
    if (Objects.isNull(name) || name.length() < 2) {
      return List.of();
    }

    return mapper.mapUserListToSearchResultList(repository.findByNameStartingWith(name));
  }

  public Author getAuthor(String name) {
    return repository.getByName(name).map(mapper::mapUserToAuthor).orElse(null);
  }

  /**
   * Used by auth process
   */
  public Optional<User> getUser(String email) {
    return repository.getByEmail(email);
  }

  public boolean addNewUser(User user) {
    if (repository.getByEmail(user.getEmail()).isPresent()) {
      return false;
    }

    repository.saveAndFlush(user);
    return true;
  }
}
