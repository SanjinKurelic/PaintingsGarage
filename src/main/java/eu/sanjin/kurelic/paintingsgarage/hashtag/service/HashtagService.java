package eu.sanjin.kurelic.paintingsgarage.hashtag.service;

import eu.sanjin.kurelic.paintingsgarage.search.model.SearchResult;
import eu.sanjin.kurelic.paintingsgarage.hashtag.entity.Hashtag;
import eu.sanjin.kurelic.paintingsgarage.hashtag.mapper.HashtagMapper;
import eu.sanjin.kurelic.paintingsgarage.hashtag.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class HashtagService {

  private final HashtagRepository hashtagRepository;
  private final HashtagMapper hashtagMapper;

  public List<SearchResult> findHashtag(String name) {
    if (Objects.isNull(name) || name.length() < 2) {
      return List.of();
    }

    return hashtagMapper.mapHashtagListToSearchResultList(hashtagRepository.findByNameStartingWith(name));
  }

  public Set<Hashtag> getOrCreate(List<String> hashtagNames) {
    var hashtags = new HashSet<Hashtag>();

    if (Objects.isNull(hashtagNames)) {
      return hashtags;
    }

    // Optimize with ehcache or redis
    hashtagNames.forEach(name -> {
      var hashTag = hashtagRepository.getByName(name);
      if (hashTag.isEmpty()) {
        hashtags.add(hashtagRepository.saveAndFlush(Hashtag.builder().name(name).build()));
      } else {
        hashtags.add(hashTag.get());
      }
    });

    return hashtags;
  }
}
