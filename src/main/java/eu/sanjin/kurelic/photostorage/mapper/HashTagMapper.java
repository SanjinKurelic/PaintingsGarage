package eu.sanjin.kurelic.photostorage.mapper;

import eu.sanjin.kurelic.photostorage.data.entity.HashTag;
import eu.sanjin.kurelic.photostorage.model.SearchModel;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ValueMapping;

import java.util.List;

@Mapper
public interface HashTagMapper {

  @Named("mapHashTagToSearchModel")
  @Mapping(target = "value", source = "name")
  @ValueMapping(target = "type", source = "HASHTAG")
  SearchModel mapHashTagToSearchModel(HashTag hashTag);

  @IterableMapping(qualifiedByName = "mapHashTagToSearchModel")
  List<SearchModel> mapHashTagListToSearchModelList(List<HashTag> hashTagList);

}
