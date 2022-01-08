package eu.sanjin.kurelic.photostorage.photo.mapper;

import eu.sanjin.kurelic.photostorage.hashtag.entity.Hashtag;
import eu.sanjin.kurelic.photostorage.photo.entity.Photo;
import eu.sanjin.kurelic.photostorage.photo.model.PhotoData;
import eu.sanjin.kurelic.photostorage.photo.model.PhotoOwnershipType;
import eu.sanjin.kurelic.photostorage.security.model.UserDetailsModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Mapper
public interface PhotoMapper {

  default PhotoOwnershipType getPhotoOwnershipType(Photo photo) {
    var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    // If user is not registered he does not have ownership type
    if (Objects.isNull(principal) || !(principal instanceof UserDetailsModel)) {
      return PhotoOwnershipType.NONE;
    }

    var currentUser = ((UserDetailsModel) principal).getId();
    // Check if user is author of image or owner (he bought it), otherwise return none
    if (photo.getAuthor().getId().equals(currentUser)) {
      return PhotoOwnershipType.OWNER;
    }
    if (photo.getOwners().stream().anyMatch(u -> u.getId().equals(currentUser))) {
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
