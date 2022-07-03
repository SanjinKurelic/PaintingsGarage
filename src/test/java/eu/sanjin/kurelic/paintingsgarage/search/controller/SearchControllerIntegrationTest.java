package eu.sanjin.kurelic.paintingsgarage.search.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sanjin.kurelic.paintingsgarage.photo.model.PhotoSize;
import eu.sanjin.kurelic.paintingsgarage.search.model.SearchResult;
import eu.sanjin.kurelic.paintingsgarage.testutil.containers.IntegrationContainerTestBase;
import eu.sanjin.kurelic.paintingsgarage.testutil.fixtures.DatabaseFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SearchControllerIntegrationTest extends IntegrationContainerTestBase {

  @Autowired
  protected ObjectMapper mapper;

  @Autowired
  private MockMvc mvc;

  @Test
  void shouldFindHashtag() throws Exception {
    // Given
    var request = MockMvcRequestBuilders
      .request(HttpMethod.GET, "/api/search/hashtag/" + DatabaseFixtures.SEARCH_TERM)
      .contentType(MediaType.APPLICATION_JSON);

    // When
    var result = mvc.perform(request).andDo(MockMvcResultHandlers.print()).andReturn();

    var response = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<SearchResult>>() {
    });

    // Then
    assertThat(response).hasSize(2);
  }

  @Test
  void shouldReturnEmptyListWhenFindHashtag() throws Exception {
    // Given
    var request = MockMvcRequestBuilders
      .request(HttpMethod.GET, "/api/search/hashtag/t")
      .contentType(MediaType.APPLICATION_JSON);

    // When
    var result = mvc.perform(request).andDo(MockMvcResultHandlers.print()).andReturn();

    var response = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<SearchResult>>() {
    });

    // Then
    assertThat(response).isEmpty();
  }

  @Test
  @WithUserDetails(value = "_test", userDetailsServiceBeanName = "userDetailsServiceImpl")
  void shouldFindAuthor() throws Exception {
    // Given
    var request = MockMvcRequestBuilders
      .request(HttpMethod.GET, "/api/search/author/" + DatabaseFixtures.SEARCH_TERM)
      .contentType(MediaType.APPLICATION_JSON);

    // When
    var result = mvc.perform(request).andDo(MockMvcResultHandlers.print()).andReturn();

    var response = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<SearchResult>>() {
    });

    // Then
    assertThat(response).hasSize(2);
  }

  @Test
  void shouldReturnEmptyListWhenFindAuthor() throws Exception {
    // Given
    var request = MockMvcRequestBuilders
      .request(HttpMethod.GET, "/api/search/author/t")
      .contentType(MediaType.APPLICATION_JSON);

    // When
    var result = mvc.perform(request).andDo(MockMvcResultHandlers.print()).andReturn();

    var response = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<SearchResult>>() {
    });

    // Then
    assertThat(response).isEmpty();
  }

  @Test
  void shouldFindPhoto() throws Exception {
    // Given
    var request = MockMvcRequestBuilders
      .request(HttpMethod.GET, "/api/search/photo")
      .param("authors", "1")
      .param("hashtags", "1")
      .param("size", PhotoSize.BIG.name())
      .param("dateFrom", LocalDateTime.now().minusYears(500).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
      .param("dateTo", LocalDateTime.now().plusYears(5000).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
      .contentType(MediaType.APPLICATION_JSON);

    // When
    var result = mvc.perform(request).andDo(MockMvcResultHandlers.print()).andReturn();

    var response = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<SearchResult>>() {
    });

    // Then
    assertThat(response).hasSize(1);
  }

  @ParameterizedTest
  @MethodSource("provideFindPhotoParameters")
  void shouldReturnEmptyListWhenFindPhoto(String authorId, String hashtagId, PhotoSize photoSize, LocalDate from, LocalDate to) throws Exception {
    // Given
    var request = MockMvcRequestBuilders
      .request(HttpMethod.GET, "/api/search/photo")
      .param("authors", authorId)
      .param("hashtags", hashtagId)
      .param("size", photoSize.name())
      .param("dateFrom", from.atStartOfDay().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
      .param("dateTo", to.atStartOfDay().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
      .contentType(MediaType.APPLICATION_JSON);

    // When
    var result = mvc.perform(request).andDo(MockMvcResultHandlers.print()).andReturn();

    var response = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<SearchResult>>() {
    });

    // Then
    assertThat(response).isEmpty();
  }

  private static Stream<Arguments> provideFindPhotoParameters() {
    return Stream.of(
      Arguments.of("2", "1", PhotoSize.BIG, LocalDate.now().minusYears(500), LocalDate.now().plusYears(5000)),
      Arguments.of("1", "2", PhotoSize.BIG, LocalDate.now().minusYears(500), LocalDate.now().plusYears(5000)),
      Arguments.of("1", "1", PhotoSize.SMALL, LocalDate.now().minusYears(500), LocalDate.now().plusYears(5000)),
      Arguments.of("1", "1", PhotoSize.BIG, LocalDate.now().plusYears(500), LocalDate.now().plusYears(5000)),
      Arguments.of("1", "1", PhotoSize.BIG, LocalDate.now().minusYears(500), LocalDate.now().minusYears(5000))
    );
  }
}