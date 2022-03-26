package eu.sanjin.kurelic.paintingsgarage.hashtag.mapper;

import eu.sanjin.kurelic.paintingsgarage.hashtag.entity.Hashtag;
import eu.sanjin.kurelic.paintingsgarage.testutil.type.UnitTest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@UnitTest
class HashtagMapperUnitTest {

  private final HashtagMapper hashtagMapper = Mappers.getMapper(HashtagMapper.class);

  @Test
  void shouldMapHashtagToSearchResult() {
    // Given
    var hashtag = new Hashtag(31L, "test");

    // When
    var result = hashtagMapper.mapHashtagToSearchResult(hashtag);

    // Then
    assertThat(result.id()).isEqualTo(hashtag.getId());
    assertThat(result.value()).isEqualTo(hashtag.getName());
  }

  @Test
  void shouldMapHashtagListToSearchResultList() {
    // Given
    var hashtags = List.of(new Hashtag(31L, "test"));

    // When
    var resultList = hashtagMapper.mapHashtagListToSearchResultList(hashtags);

    // Then
    assertThat(resultList).hasSize(1);
    assertThat(resultList.get(0).id()).isEqualTo(hashtags.get(0).getId());
    assertThat(resultList.get(0).value()).isEqualTo(hashtags.get(0).getName());
  }
}