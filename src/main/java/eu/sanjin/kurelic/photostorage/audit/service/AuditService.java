package eu.sanjin.kurelic.photostorage.audit.service;

import eu.sanjin.kurelic.photostorage.audit.entity.Audit;
import eu.sanjin.kurelic.photostorage.audit.entity.AuditAction;
import eu.sanjin.kurelic.photostorage.audit.mapper.AuditMapper;
import eu.sanjin.kurelic.photostorage.audit.model.AuditModel;
import eu.sanjin.kurelic.photostorage.audit.repository.AuditRepository;
import eu.sanjin.kurelic.photostorage.common.exceptions.InternalServerError;
import eu.sanjin.kurelic.photostorage.photo.model.PhotoData;
import eu.sanjin.kurelic.photostorage.security.model.UserDetailsModel;
import eu.sanjin.kurelic.photostorage.user.entity.UserPlan;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuditService {

  private final AuditRepository repository;
  private final AuditMapper mapper;

  public List<AuditModel> getLatestAuditLogs() {
    return mapper.mapAuditListToAuditModelList(repository.findFirst10ByOrderByTimeDesc());
  }

  private String getCurrentUser() {
    var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    // Throw exception if somehow user called secured endpoint
    if (Objects.isNull(principal) || !(principal instanceof UserDetailsModel currentUser)) {
      throw new InternalServerError("User called restricted endpoint without log in!");
    }

    return currentUser.getName();
  }

  private void logAudit(AuditAction action, String object) {
    repository.saveAndFlush(
      Audit.builder()
        .time(LocalDateTime.now())
        .action(action)
        .subject(getCurrentUser())
        .object(object)
        .build()
    );
  }

  public void logPhotoChanges(PhotoData photo, AuditAction action) {
    logAudit(action, photo.path());
  }

  public void logAuthChanges(AuditAction action) {
    logAudit(action, null);
  }

  public void logUserChanges(UserPlan userPlan) {
    if (Objects.isNull(userPlan)) {
      // Double check if no exception is thrown in annotated logic
      return;
    }
    logAudit(AuditAction.USER_CHANGE_PLAN, userPlan.name());
  }
}
