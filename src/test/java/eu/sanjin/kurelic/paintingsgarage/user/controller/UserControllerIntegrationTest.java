package eu.sanjin.kurelic.paintingsgarage.user.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sanjin.kurelic.paintingsgarage.testutil.fixtures.DatabaseFixtures;
import eu.sanjin.kurelic.paintingsgarage.testutil.type.IntegrationTest;
import eu.sanjin.kurelic.paintingsgarage.user.entity.UserPlan;
import eu.sanjin.kurelic.paintingsgarage.user.model.Author;
import eu.sanjin.kurelic.paintingsgarage.user.model.UserPlanRequest;
import eu.sanjin.kurelic.paintingsgarage.user.repository.UserRepository;
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

@IntegrationTest
class UserControllerIntegrationTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  protected ObjectMapper mapper;

  @Autowired
  private MockMvc mvc;

  @Test
  @WithUserDetails(value = "_test", userDetailsServiceBeanName = "userDetailsServiceImpl")
  void getCurrentAuthor() throws Exception {
    // Given
    var request = MockMvcRequestBuilders
      .request(HttpMethod.GET, "/api/user/current")
      .contentType(MediaType.APPLICATION_JSON);

    // When
    var result = mvc.perform(request).andDo(MockMvcResultHandlers.print()).andReturn();

    var response = mapper.readValue(result.getResponse().getContentAsString(), Author.class);

    // Then
    assertThat(response.email()).isEqualTo(DatabaseFixtures.USER_EMAIL);
  }

  @Test
  @WithUserDetails(value = "_admin", userDetailsServiceBeanName = "userDetailsServiceImpl")
  void getAuthorList() throws Exception {
    // Given
    var request = MockMvcRequestBuilders
      .request(HttpMethod.GET, "/api/user")
      .contentType(MediaType.APPLICATION_JSON);

    // When
    var result = mvc.perform(request).andDo(MockMvcResultHandlers.print()).andReturn();

    var response = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Author>>() {
    });

    // Then
    assertThat(response).isNotEmpty();
  }

  @Test
  @WithUserDetails(value = "_test", userDetailsServiceBeanName = "userDetailsServiceImpl")
  void updateUser() throws Exception {
    // Given
    var request = MockMvcRequestBuilders
      .request(HttpMethod.PUT, "/api/user/" + DatabaseFixtures.USER_ID)
      .content(mapper.writeValueAsString(new UserPlanRequest(UserPlan.BUYER)))
      .contentType(MediaType.APPLICATION_JSON);

    // When
    mvc.perform(request).andDo(MockMvcResultHandlers.print()).andReturn();

    var updatedUser = userRepository.getByEmail(DatabaseFixtures.USER_EMAIL);

    // Then
    assertThat(updatedUser).isNotEmpty();
    assertThat(updatedUser.get().getPlan()).isEqualTo(UserPlan.BUYER);
  }
}