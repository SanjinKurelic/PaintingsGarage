package eu.sanjin.kurelic.paintingsgarage.audit.service;

import eu.sanjin.kurelic.paintingsgarage.audit.entity.Audit;
import eu.sanjin.kurelic.paintingsgarage.audit.entity.AuditAction;
import eu.sanjin.kurelic.paintingsgarage.audit.mapper.AuditMapper;
import eu.sanjin.kurelic.paintingsgarage.audit.model.AuditModel;
import eu.sanjin.kurelic.paintingsgarage.audit.repository.AuditRepository;
import eu.sanjin.kurelic.paintingsgarage.photo.model.PhotoData;
import eu.sanjin.kurelic.paintingsgarage.security.model.UserDetailsModel;
import eu.sanjin.kurelic.paintingsgarage.testutil.type.UnitTest;
import eu.sanjin.kurelic.paintingsgarage.user.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@UnitTest
class AuditServiceTest {

  @Mock
  private AuditRepository repository;

  @Mock
  private AuditMapper mapper;

  @InjectMocks
  private AuditService service;

  @BeforeAll
  static void init() {
    var securityContext = mock(SecurityContext.class);
    var authentication = mock(Authentication.class);
    var userDetailsModel = mock(UserDetailsModel.class);

    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(userDetailsModel);
    when(userDetailsModel.getName()).thenReturn("Some user");

    SecurityContextHolder.setContext(securityContext);
  }

  @Test
  void shouldGetLatestAuditLogs() {
    // Given
    var auditList = List.of(mock(Audit.class));

    when(repository.findFirst10ByOrderByTimeDesc()).thenReturn(auditList);
    when(mapper.mapAuditListToAuditModelList(auditList)).thenReturn(List.of(mock(AuditModel.class)));

    // When
    var exception = catchThrowable(() -> service.getLatestAuditLogs());

    // Then
    assertThat(exception).doesNotThrowAnyException();
    verify(repository, times(1)).findFirst10ByOrderByTimeDesc();
    verify(mapper, times(1)).mapAuditListToAuditModelList(auditList);
  }

  @Test
  void shouldLogPhotoChanges() {
    // Given
    var action = mock(AuditAction.class);
    var photoData = mock(PhotoData.class);

    when(photoData.path()).thenReturn("Some path");
    when(repository.saveAndFlush(any())).thenReturn(mock(Audit.class));

    // When
    var exception = catchThrowable(() -> service.logPhotoChanges(photoData, action));

    // Then
    assertThat(exception).doesNotThrowAnyException();
    verify(repository, times(1)).saveAndFlush(any());
  }

  @Test
  void shouldLogAuthChange() {
    // Given
    var action = mock(AuditAction.class);

    when(repository.saveAndFlush(any())).thenReturn(mock(Audit.class));

    // When
    var exception = catchThrowable(() -> service.logAuthChanges(action));

    // Then
    assertThat(exception).doesNotThrowAnyException();
    verify(repository, times(1)).saveAndFlush(any());
  }

  @Test
  void shouldLogUserChange() {
    // Given
    var userPlan = "Some user plan";
    var user = new User();
    user.setName("Test");

    when(repository.saveAndFlush(any())).thenReturn(mock(Audit.class));

    // When
    var exception = catchThrowable(() -> service.logUserChanges(user, userPlan));

    // Then
    assertThat(exception).doesNotThrowAnyException();
    verify(repository, times(1)).saveAndFlush(any());
  }
}