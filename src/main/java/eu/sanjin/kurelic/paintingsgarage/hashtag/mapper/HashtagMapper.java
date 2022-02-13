package eu.sanjin.kurelic.paintingsgarage.hashtag.mapper;

import eu.sanjin.kurelic.paintingsgarage.search.model.SearchResult;
import eu.sanjin.kurelic.paintingsgarage.hashtag.entity.Hashtag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface HashtagMapper {

  @Mapping(target = "value", source = "name")
  SearchResult mapHashtagToSearchResult(Hashtag hashtag);

  List<SearchResult> mapHashtagListToSearchResultList(List<Hashtag> hashtagList);
}
