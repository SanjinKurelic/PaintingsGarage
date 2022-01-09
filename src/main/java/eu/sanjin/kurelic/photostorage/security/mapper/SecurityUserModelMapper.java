package eu.sanjin.kurelic.photostorage.security.mapper;

import eu.sanjin.kurelic.photostorage.user.entity.User;
import eu.sanjin.kurelic.photostorage.user.entity.UserPlan;
import eu.sanjin.kurelic.photostorage.user.entity.UserRole;
import eu.sanjin.kurelic.photostorage.security.model.RegisterUserRequest;
import eu.sanjin.kurelic.photostorage.security.model.UserDetailsModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.List;
import java.util.Objects;

@Mapper
public interface SecurityUserModelMapper {

  // User can have only one role
  default List<SimpleGrantedAuthority> mapRoleToGrantedAuthority(UserRole role) {
    return List.of(
      new SimpleGrantedAuthority(role.name())
    );
  }

  @Mapping(target = "enabled", source = "active")
  @Mapping(target = "authorities", source = "role")
  UserDetailsModel mapUserToUserDetails(User user);

  default String encodePassword(RegisterUserRequest registerUserRequest) {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(registerUserRequest.password());
  }

  default UserPlan mapIntegerToUserPlan(Integer plan) {
    if (Objects.isNull(plan)) {
      return null;
    }

    return switch (plan) {
      case 0 -> UserPlan.BUYER;
      case 1 -> UserPlan.ARTIST;
      default -> null;
    };
  }

  @Mapping(target = "role", constant = "ROLE_USER")
  @Mapping(target = "active", constant = "true") // Should be true after mail confirmation
  @Mapping(target = "password", source = ".")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "registered", ignore = true)
  @Mapping(target = "avatar", constant = "user.png")
  User mapRegisterUserToUser(RegisterUserRequest registerUserRequest);
}
