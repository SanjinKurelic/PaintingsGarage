package eu.sanjin.kurelic.paintingsgarage.audit.aspect;

import eu.sanjin.kurelic.paintingsgarage.audit.entity.AuditAction;
import eu.sanjin.kurelic.paintingsgarage.audit.service.AuditService;
import eu.sanjin.kurelic.paintingsgarage.photo.model.PhotoData;
import eu.sanjin.kurelic.paintingsgarage.photo.service.PhotoService;
import eu.sanjin.kurelic.paintingsgarage.testutil.type.UnitTest;
import eu.sanjin.kurelic.paintingsgarage.user.entity.User;
import eu.sanjin.kurelic.paintingsgarage.user.entity.UserPlan;
import eu.sanjin.kurelic.paintingsgarage.user.model.UserPlanRequest;
import eu.sanjin.kurelic.paintingsgarage.user.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@UnitTest
class LogAspectUnitTest {

  @Mock
  private AuditService auditService;

  @Mock
  private PhotoService photoService;

  @Mock
  private UserService userService;

  @InjectMocks
  private LogAspect logAspect;

  @Test
  void shouldLogPhotoBought() {
    // Given
    var photoList = List.of(mock(PhotoData.class));
    var photos = ResponseEntity.ok(photoList);

    doNothing().when(auditService).logPhotoChanges(any(), eq(AuditAction.PICTURE_BOUGHT));

    // When
    var exception = catchThrowable(() -> logAspect.logPhotoBought(photos));

    // Then
    assertThat(exception).doesNotThrowAnyException();
    verify(auditService, times(1)).logPhotoChanges(any(), eq(AuditAction.PICTURE_BOUGHT));
  }

  @Test
  void shouldThrowExceptionWhenLogPhotoBought() {
    // Given
    var photos = ResponseEntity.ok((List<PhotoData>) null);

    // When
    var exception = catchThrowable(() -> logAspect.logPhotoBought(photos));

    // Then
    assertThat(exception).isInstanceOf(NullPointerException.class);
  }

  @Test
  void shouldLogPhotoDelete() throws Throwable {
    // Given
    var photoData = mock(PhotoData.class);
    var joinPoint = mock(ProceedingJoinPoint.class);

    when(joinPoint.getArgs()).thenReturn(new Object[]{1L});
    when(photoService.getPhoto(1L)).thenReturn(photoData);
    when(joinPoint.proceed()).thenReturn("Some result");
    doNothing().when(auditService).logPhotoChanges(photoData, AuditAction.PICTURE_DELETE);

    // When
    var exception = catchThrowable(() -> logAspect.logPhotoDelete(joinPoint));

    // Then
    assertThat(exception).doesNotThrowAnyException();
    verify(photoService, times(1)).getPhoto(1L);
    verify(auditService, times(1)).logPhotoChanges(photoData, AuditAction.PICTURE_DELETE);
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenLogPhotoDelete(String data) throws Throwable {
    // Given
    var joinPoint = mock(ProceedingJoinPoint.class);

    when(joinPoint.getArgs()).thenReturn(Objects.isNull(data) ? new Object[]{} : new Object[]{data});
    when(joinPoint.proceed()).thenReturn("Some result");

    // When
    var exception = catchThrowable(() -> logAspect.logPhotoDelete(joinPoint));

    // Then
    assertThat(exception).isInstanceOf(NullPointerException.class);
    verifyNoInteractions(photoService, auditService);
  }

  @Test
  void shouldLogPhotoUpdate() {
    // Given
    var photoData = mock(PhotoData.class);
    var responseEntity = ResponseEntity.ok(photoData);

    doNothing().when(auditService).logPhotoChanges(photoData, AuditAction.PICTURE_CHANGE);

    // When
    var exception = catchThrowable(() -> logAspect.logPhotoUpdate(responseEntity));

    // Then
    assertThat(exception).doesNotThrowAnyException();
    verify(auditService, times(1)).logPhotoChanges(photoData, AuditAction.PICTURE_CHANGE);
  }

  @Test
  void shouldLogPhotoUpload() {
    // Given
    var photoData = mock(PhotoData.class);
    var responseEntity = ResponseEntity.ok(photoData);

    doNothing().when(auditService).logPhotoChanges(photoData, AuditAction.PICTURE_UPLOAD);

    // When
    var exception = catchThrowable(() -> logAspect.logPhotoUpload(responseEntity));

    // Then
    assertThat(exception).doesNotThrowAnyException();
    verify(auditService, times(1)).logPhotoChanges(photoData, AuditAction.PICTURE_UPLOAD);
  }

  @Test
  void shouldLogUserLogin() {
    // Given
    doNothing().when(auditService).logAuthChanges(AuditAction.USER_LOGIN);

    // When
    var exception = catchThrowable(() -> logAspect.logUserLogin());

    // Then
    assertThat(exception).doesNotThrowAnyException();
    verify(auditService, times(1)).logAuthChanges(AuditAction.USER_LOGIN);
  }

  @Test
  void shouldLogUserRegistration() {
    // Given
    doNothing().when(auditService).logAuthChanges(AuditAction.USER_REGISTER);

    // When
    var exception = catchThrowable(() -> logAspect.logUserRegistration());

    // Then
    assertThat(exception).doesNotThrowAnyException();
    verify(auditService, times(1)).logAuthChanges(AuditAction.USER_REGISTER);
  }

  @Test
  void shouldLogUserPlanChange() throws Throwable {
    // Given
    var userId = 1L;
    var userPlan = UserPlan.ARTIST;
    var joinPoint = mock(ProceedingJoinPoint.class);
    var userPlanRequest = mock(UserPlanRequest.class);

    when(joinPoint.getArgs()).thenReturn(new Object[]{userId, userPlanRequest});
    when(userService.getUser(userId)).thenReturn(mock(User.class));
    when(userPlanRequest.plan()).thenReturn(userPlan);
    when(joinPoint.proceed()).thenReturn(null);
    doNothing().when(auditService).logUserChanges(any(), eq(userPlan.name()));

    // When
    var throwable = catchThrowable(() -> logAspect.logUserPlanChange(joinPoint));

    // Then
    assertThat(throwable).doesNotThrowAnyException();
    verify(userService, times(1)).getUser(userId);
    verify(auditService, times(1)).logUserChanges(any(), eq(userPlan.name()));
  }

  @ParameterizedTest
  @MethodSource("provideUserPlanChangeArguments")
  void shouldThrowExceptionWhenLogUserPlanChange(List<Object> arguments) throws Throwable {
    // Given
    var joinPoint = mock(ProceedingJoinPoint.class);

    when(joinPoint.getArgs()).thenReturn(arguments.toArray());
    when(joinPoint.proceed()).thenReturn(null);

    // When
    var throwable = catchThrowable(() -> logAspect.logUserPlanChange(joinPoint));

    // Then
    assertThat(throwable).isInstanceOf(NullPointerException.class);
  }

  private static Stream<Arguments> provideUserPlanChangeArguments() {
    return Stream.of(
      Arguments.of(List.of()),
      Arguments.of(List.of(1L)),
      Arguments.of(List.of("abc", new UserPlanRequest(UserPlan.ARTIST))),
      Arguments.of(List.of(1L, new Object()))
    );
  }
}