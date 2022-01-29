package eu.sanjin.kurelic.photostorage.audit.aspect;

import eu.sanjin.kurelic.photostorage.audit.entity.AuditAction;
import eu.sanjin.kurelic.photostorage.audit.service.AuditService;
import eu.sanjin.kurelic.photostorage.photo.model.PhotoData;
import eu.sanjin.kurelic.photostorage.user.entity.UserPlan;
import eu.sanjin.kurelic.photostorage.user.model.UserPlanRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

  private final AuditService auditService;

  @AfterReturning(value = "@annotation(LogPhotoUpload)", returning = "photo")
  public void logPhotoUpload(PhotoData photo) {
    auditService.logPhotoChanges(photo, AuditAction.PICTURE_UPLOAD);
  }

  @AfterReturning("@annotation(LogUserLogin)")
  public void logUserLogin() {
    auditService.logAuthChanges(AuditAction.USER_LOGIN);
  }

  @Around("@annotation(LogUserPlanChange)")
  public Object logPhotoUpload(ProceedingJoinPoint joinPoint) throws Throwable {
    UserPlan userPlan = null;
    // resolve user plan
    var args = joinPoint.getArgs();
    if (args.length >= 1 && args[0] instanceof UserPlanRequest userPlanRequest) {
      userPlan = userPlanRequest.plan();
    }

    var result = joinPoint.proceed();

    // If no exception log action
    auditService.logUserChanges(userPlan);

    return result;
  }

  @AfterReturning("@annotation(LogUserRegistration)")
  public void logUserRegistration() {
    auditService.logAuthChanges(AuditAction.USER_REGISTER);
  }
}
