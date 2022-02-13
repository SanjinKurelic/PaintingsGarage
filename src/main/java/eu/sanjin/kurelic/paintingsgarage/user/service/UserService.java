package eu.sanjin.kurelic.paintingsgarage.user.service;

import eu.sanjin.kurelic.paintingsgarage.search.model.SearchResult;
import eu.sanjin.kurelic.paintingsgarage.security.model.UserDetailsModel;
import eu.sanjin.kurelic.paintingsgarage.user.entity.User;
import eu.sanjin.kurelic.paintingsgarage.user.entity.UserPlan;
import eu.sanjin.kurelic.paintingsgarage.user.mapper.UserMapper;
import eu.sanjin.kurelic.paintingsgarage.user.model.Author;
import eu.sanjin.kurelic.paintingsgarage.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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

  public Author getAuthorDetails() {
    var user = (UserDetailsModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    return repository.getByName(user.getName()).map(mapper::mapUserToAuthor).orElse(null);
  }

  public List<Author> getAuthors() {
    return mapper.mapUserListToAuthorList(repository.getAllByActiveIsTrue());
  }

  public void changePlan(Long userId, UserPlan plan) {
    var userData = repository.findById(userId).orElseThrow();

    userData.setPlan(plan);

    repository.saveAndFlush(userData);
  }

  public User getUser(Long userId) {
    return repository.getById(userId);
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
