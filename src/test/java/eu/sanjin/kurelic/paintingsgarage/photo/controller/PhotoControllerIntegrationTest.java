package eu.sanjin.kurelic.paintingsgarage.photo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sanjin.kurelic.paintingsgarage.photo.model.PhotoData;
import eu.sanjin.kurelic.paintingsgarage.testutil.containers.IntegrationContainerTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PhotoControllerIntegrationTest extends IntegrationContainerTestBase {

  @Autowired
  protected ObjectMapper mapper;

  @Autowired
  private MockMvc mvc;

  @Test
  @WithUserDetails(value = "_test", userDetailsServiceBeanName = "userDetailsServiceImpl")
  void getPhotoList() throws Exception {
    // Given
    var request = MockMvcRequestBuilders
      .request(HttpMethod.GET, "/api/photo")
      .contentType(MediaType.APPLICATION_JSON);

    // When
    var result = mvc.perform(request).andDo(MockMvcResultHandlers.print()).andReturn();

    var response = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<PhotoData>>() {
    });

    // Then
    assertThat(response).hasSize(1);
  }
}