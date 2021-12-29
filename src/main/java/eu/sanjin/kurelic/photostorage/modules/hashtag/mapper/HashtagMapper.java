package eu.sanjin.kurelic.photostorage.modules.hashtag.mapper;

import eu.sanjin.kurelic.photostorage.common.model.SearchResult;
import eu.sanjin.kurelic.photostorage.modules.hashtag.entity.Hashtag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface HashtagMapper {

  @Mapping(target = "value", source = "name")
  SearchResult mapHashtagToSearchResult(Hashtag hashtag);

  List<SearchResult> mapHashtagListToSearchResultList(List<Hashtag> hashtagList);

}
