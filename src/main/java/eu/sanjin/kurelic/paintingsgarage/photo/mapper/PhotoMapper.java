package eu.sanjin.kurelic.paintingsgarage.photo.mapper;

import eu.sanjin.kurelic.paintingsgarage.hashtag.entity.Hashtag;
import eu.sanjin.kurelic.paintingsgarage.photo.entity.Photo;
import eu.sanjin.kurelic.paintingsgarage.photo.model.PhotoData;
import eu.sanjin.kurelic.paintingsgarage.photo.model.PhotoOwnershipType;
import eu.sanjin.kurelic.paintingsgarage.security.model.UserDetailsModel;
import eu.sanjin.kurelic.paintingsgarage.user.entity.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Mapper
public interface PhotoMapper {

  default PhotoOwnershipType getPhotoOwnershipType(Photo photo) {
    var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    // If user is not registered he does not have ownership type
    if (Objects.isNull(principal) || !(principal instanceof UserDetailsModel currentUser)) {
      return PhotoOwnershipType.NONE;
    }

    // If user is admin he owns every photo
    var adminRole = UserRole.ROLE_ADMIN.name();
    if (currentUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(adminRole::equals)) {
      return PhotoOwnershipType.OWNER;
    }
    // Check if user is author or owner (he/she bought it) of an image, otherwise return none
    if (photo.getAuthor().getId().equals(currentUser.getId())) {
      return PhotoOwnershipType.OWNER;
    }
    if (photo.getOwners().stream().anyMatch(u -> u.getId().equals(currentUser.getId()))) {
      return PhotoOwnershipType.BOUGHT;
    }
    return PhotoOwnershipType.NONE;
  }

  @Mapping(target = "author", source = "author.name")
  @Mapping(target = "ownershipType", source = ".")
  PhotoData mapPhotoToPhotoData(Photo photo);

  List<PhotoData> mapPhotoListToPhotoDataList(List<Photo> photoList);

  default String mapHashtagToString(Hashtag hashtag) {
    return Optional.ofNullable(hashtag).map(Hashtag::getName).orElse(null);
  }

  List<String> mapHashtagListToStringList(List<Hashtag> hashtagList);
}
