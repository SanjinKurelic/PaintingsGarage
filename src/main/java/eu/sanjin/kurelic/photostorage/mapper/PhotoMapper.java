package eu.sanjin.kurelic.photostorage.mapper;

import eu.sanjin.kurelic.photostorage.data.entity.HashTag;
import eu.sanjin.kurelic.photostorage.data.entity.Photo;
import eu.sanjin.kurelic.photostorage.model.PhotoData;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PhotoMapper {

  @Named("mapPhotoToPhotoData")
  @Mapping(target = "author", source = "author.username")
  PhotoData mapPhotoToPhotoData(Photo photo);

  @IterableMapping(qualifiedByName = "mapPhotoToPhotoData")
  List<PhotoData> mapPhotoListToPhotoDataList(List<Photo> photoList);

  @Named("mapHashTagToString")
  default String mapHashTagToString(HashTag hashTag) {
    return Optional.ofNullable(hashTag).map(HashTag::getName).orElse(null);
  }

  @IterableMapping(qualifiedByName = "mapHashTagToString")
  List<String> mapHashTagListToStringList(List<HashTag> hashTagList);

}
