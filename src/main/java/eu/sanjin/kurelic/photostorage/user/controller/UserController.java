package eu.sanjin.kurelic.photostorage.user.controller;

import eu.sanjin.kurelic.photostorage.audit.aspect.LogUserPlanChange;
import eu.sanjin.kurelic.photostorage.common.model.SearchResult;
import eu.sanjin.kurelic.photostorage.security.model.UserDetailsModel;
import eu.sanjin.kurelic.photostorage.user.model.Author;
import eu.sanjin.kurelic.photostorage.user.model.UserPlanRequest;
import eu.sanjin.kurelic.photostorage.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    Author author = service.getAuthorDetails();

    if (Objects.isNull(author)) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(author);
  }

  @GetMapping("/list")
  public ResponseEntity<List<Author>> getAuthorList() {
    return ResponseEntity.ok(service.getAuthors());
  }

  @LogUserPlanChange
  @PostMapping("/changePlan")
  public void changePlan(@RequestBody UserPlanRequest plan) {
    var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (Objects.nonNull(plan.plan()) && Objects.nonNull(principal) && principal instanceof UserDetailsModel user) {
      service.changePlan(user.getId(), plan.plan());
    }
  }

  @PostMapping("/changePlanForUser/{:userId}")
  public void changePlanForUser(@PathVariable Long userId, @RequestBody UserPlanRequest plan) {
    if (Objects.nonNull(plan.plan())) {
      service.changePlan(userId, plan.plan());
    }
  }

  @GetMapping("/find/{authorName}")
  public ResponseEntity<List<SearchResult>> findAuthor(@PathVariable String authorName) {
    return ResponseEntity.ok(service.findAuthor(authorName));
  }
}
