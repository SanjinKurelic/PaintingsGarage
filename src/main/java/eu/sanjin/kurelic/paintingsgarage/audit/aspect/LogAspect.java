package eu.sanjin.kurelic.paintingsgarage.audit.aspect;

import eu.sanjin.kurelic.paintingsgarage.audit.entity.AuditAction;
import eu.sanjin.kurelic.paintingsgarage.audit.service.AuditService;
import eu.sanjin.kurelic.paintingsgarage.photo.model.PhotoData;
import eu.sanjin.kurelic.paintingsgarage.photo.service.PhotoService;
import eu.sanjin.kurelic.paintingsgarage.user.entity.User;
import eu.sanjin.kurelic.paintingsgarage.user.model.UserPlanRequest;
import eu.sanjin.kurelic.paintingsgarage.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

  private final AuditService auditService;
  private final PhotoService photoService;
  private final UserService userService;

  // Photo

  @AfterReturning(value = "@annotation(LogPhotoBought)", returning = "photos")
  public void logPhotoBought(ResponseEntity<List<PhotoData>> photos) {
    Objects.requireNonNull(photos.getBody()).forEach(photo -> auditService.logPhotoChanges(photo, AuditAction.PICTURE_BOUGHT));
  }

  @Around("@annotation(LogPhotoDelete)")
  public Object logPhotoDelete(ProceedingJoinPoint joinPoint) throws Throwable {
    PhotoData photo = null;
    // resolve user plan
    var args = joinPoint.getArgs();
    if (args.length >= 1 && args[0] instanceof Long requestedPhotoId) {
      photo = photoService.getPhoto(requestedPhotoId);
    }

    var result = joinPoint.proceed();

    // If no exception log action
    auditService.logPhotoChanges(Objects.requireNonNull(photo), AuditAction.PICTURE_DELETE);

    return result;
  }

  @AfterReturning(value = "@annotation(LogPhotoUpdate)", returning = "photo")
  public void logPhotoUpdate(ResponseEntity<PhotoData> photo) {
    auditService.logPhotoChanges(Objects.requireNonNull(photo.getBody()), AuditAction.PICTURE_CHANGE);
  }

  @AfterReturning(value = "@annotation(LogPhotoUpload)", returning = "photo")
  public void logPhotoUpload(ResponseEntity<PhotoData> photo) {
    auditService.logPhotoChanges(Objects.requireNonNull(photo.getBody()), AuditAction.PICTURE_UPLOAD);
  }

  // User

  @AfterReturning("@annotation(LogUserLogin)")
  public void logUserLogin() {
    auditService.logAuthChanges(AuditAction.USER_LOGIN);
  }

  @AfterReturning("@annotation(LogUserRegistration)")
  public void logUserRegistration() {
    auditService.logAuthChanges(AuditAction.USER_REGISTER);
  }

  @Around("@annotation(LogUserPlanChange)")
  public Object logPhotoUpload(ProceedingJoinPoint joinPoint) throws Throwable {
    String userPlan = null;
    User user = null;
    // resolve user plan
    var args = joinPoint.getArgs();
    if (args.length >= 2 && args[0] instanceof Long userIdRequest && args[1] instanceof UserPlanRequest userPlanRequest) {
      user = userService.getUser(userIdRequest);
      userPlan = Optional.ofNullable(userPlanRequest.plan()).map(Enum::name).orElse(null);
    }

    var result = joinPoint.proceed();

    // If no exception log action
    auditService.logUserChanges(Objects.requireNonNull(user), userPlan);

    return result;
  }
}
