package eu.sanjin.kurelic.photostorage.user.controller;

import eu.sanjin.kurelic.photostorage.audit.aspect.LogUserPlanChange;
import eu.sanjin.kurelic.photostorage.security.model.UserDetailsModel;
import eu.sanjin.kurelic.photostorage.user.entity.UserRole;
import eu.sanjin.kurelic.photostorage.user.model.Author;
import eu.sanjin.kurelic.photostorage.user.model.UserPlanRequest;
import eu.sanjin.kurelic.photostorage.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

  @GetMapping("/current")
  public ResponseEntity<Author> getCurrentAuthor() {
    Author author = service.getAuthorDetails();

    if (Objects.isNull(author)) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(author);
  }

  @GetMapping
  public ResponseEntity<List<Author>> getAuthorList() {
    return ResponseEntity.ok(service.getAuthors());
  }

  @LogUserPlanChange
  @PutMapping("/{userId}")
  public void updateUser(@PathVariable Long userId, @RequestBody UserPlanRequest plan) {
    var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (Objects.nonNull(plan.plan()) && Objects.nonNull(principal) && principal instanceof UserDetailsModel user) {
      // If user does not update data for himself, or he does not have admin role, block attempt
      if (!user.getId().equals(userId)
        && user.getAuthorities().stream().map(GrantedAuthority::getAuthority).noneMatch(UserRole.ROLE_ADMIN.name()::equals)) {
        return;
      }

      service.changePlan(userId, plan.plan());
    }
  }
}
