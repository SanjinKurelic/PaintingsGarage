package eu.sanjin.kurelic.paintingsgarage.hashtag.service;

import eu.sanjin.kurelic.paintingsgarage.hashtag.entity.Hashtag;
import eu.sanjin.kurelic.paintingsgarage.hashtag.mapper.HashtagMapper;
import eu.sanjin.kurelic.paintingsgarage.hashtag.repository.HashtagRepository;
import eu.sanjin.kurelic.paintingsgarage.search.model.SearchResult;
import eu.sanjin.kurelic.paintingsgarage.testutil.type.UnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@UnitTest
class HashtagServiceUnitTest {

  @Mock
  private HashtagRepository hashtagRepository;

  @Mock
  private HashtagMapper hashtagMapper;

  @InjectMocks
  private HashtagService hashtagService;

  @Test
  void shouldFindHashtag() {
    // Given
    var name = "test";

    when(hashtagRepository.findByNameStartingWith(name)).thenReturn(List.of(mock(Hashtag.class)));
    when(hashtagMapper.mapHashtagListToSearchResultList(any())).thenReturn(List.of(mock(SearchResult.class)));

    // When
    var result = hashtagService.findHashtag(name);

    // Then
    assertThat(result).hasSize(1);
    verify(hashtagRepository, times(1)).findByNameStartingWith(name);
    verify(hashtagMapper, times(1)).mapHashtagListToSearchResultList(any());
  }

  @Test
  void shouldReturnEmptyListWhenFindHashtag() {
    // Given
    var name = "t";

    // When
    var result = hashtagService.findHashtag(name);

    // Then
    assertThat(result).isEmpty();
  }

  @Test
  void shouldGetOrCreate() {
    // Given
    var hashtagNames = List.of("test", "abc");
    var hashtag = Hashtag.builder().name(hashtagNames.get(1)).build();

    when(hashtagRepository.getByName(hashtagNames.get(0))).thenReturn(Optional.of(mock(Hashtag.class)));
    when(hashtagRepository.getByName(hashtagNames.get(1))).thenReturn(Optional.empty());
    when(hashtagRepository.saveAndFlush(any())).thenReturn(hashtag);

    // When
    var result = hashtagService.getOrCreate(hashtagNames);

    // Then
    assertThat(result).hasSize(2);
    verify(hashtagRepository, times(1)).getByName(hashtagNames.get(0));
    verify(hashtagRepository, times(1)).getByName(hashtagNames.get(1));
    verify(hashtagRepository, times(1)).saveAndFlush(any());
  }

  @Test
  void shouldReturnEmptyListWhenGetOrCreate() {
    // When
    var result = hashtagService.getOrCreate(null);

    // Then
    assertThat(result).isEmpty();
  }
}