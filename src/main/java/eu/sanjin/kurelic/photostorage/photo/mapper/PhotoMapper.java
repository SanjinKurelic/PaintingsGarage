package eu.sanjin.kurelic.photostorage.photo.mapper;

import eu.sanjin.kurelic.photostorage.hashtag.entity.Hashtag;
import eu.sanjin.kurelic.photostorage.photo.entity.Photo;
import eu.sanjin.kurelic.photostorage.photo.model.PhotoData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PhotoMapper {

  @Mapping(target = "author", source = "author.name")
  @Mapping(target = "ownershipType", constant = "NONE")
    // TODO check user ownership
  PhotoData mapPhotoToPhotoData(Photo photo);

  List<PhotoData> mapPhotoListToPhotoDataList(List<Photo> photoList);

  default String mapHashtagToString(Hashtag hashtag) {
    return Optional.ofNullable(hashtag).map(Hashtag::getName).orElse(null);
  }

  List<String> mapHashtagListToStringList(List<Hashtag> hashtagList);
}
