package eu.sanjin.kurelic.photostorage.user.controller;

import eu.sanjin.kurelic.photostorage.common.model.SearchResult;
import eu.sanjin.kurelic.photostorage.security.model.UserDetailsModel;
import eu.sanjin.kurelic.photostorage.user.model.Author;
import eu.sanjin.kurelic.photostorage.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

  private final UserService service;

  @GetMapping("/details")
  public ResponseEntity<Author> getCurrentAuthor() {
    Author author = null;

    // Get current user
    var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (Objects.nonNull(principal) && principal instanceof UserDetailsModel user) {
      author = service.getAuthor(user.getName());
    }

    if (Objects.isNull(author)) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(author);
  }

  @GetMapping("/details/{authorName}")
  public ResponseEntity<Author> getUserDetails(@PathVariable String authorName) {
    var author = service.getAuthor(authorName);

    if (Objects.isNull(author)) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(author);
  }

  @GetMapping("/find/{authorName}")
  public ResponseEntity<List<SearchResult>> findAuthor(@PathVariable String authorName) {
    return ResponseEntity.ok(service.findAuthor(authorName));
  }
}
