package eu.sanjin.kurelic.photostorage.hashtag.mapper;

import eu.sanjin.kurelic.photostorage.search.model.SearchResult;
import eu.sanjin.kurelic.photostorage.hashtag.entity.Hashtag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface HashtagMapper {

  @Mapping(target = "value", source = "name")
  SearchResult mapHashtagToSearchResult(Hashtag hashtag);

  List<SearchResult> mapHashtagListToSearchResultList(List<Hashtag> hashtagList);
}
