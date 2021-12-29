package eu.sanjin.kurelic.photostorage.modules.hashtag.service;

import eu.sanjin.kurelic.photostorage.common.model.SearchResult;
import eu.sanjin.kurelic.photostorage.modules.hashtag.mapper.HashtagMapper;
import eu.sanjin.kurelic.photostorage.modules.hashtag.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagService {

  private final HashtagRepository hashtagRepository;
  private final HashtagMapper hashtagMapper;

  public List<SearchResult> findHashtag(String name) {
    if (name.length() < 2) {
      return List.of();
    }

    return hashtagMapper.mapHashtagListToSearchResultList(hashtagRepository.findByNameStartingWith(name));
  }
}
