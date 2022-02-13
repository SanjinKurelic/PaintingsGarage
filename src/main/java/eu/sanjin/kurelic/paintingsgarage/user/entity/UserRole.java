package eu.sanjin.kurelic.paintingsgarage.user.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserRole {
  ROLE_ADMIN("ADMIN"), ROLE_USER("USER");

  @Getter
  private final String applicationRole;
}
