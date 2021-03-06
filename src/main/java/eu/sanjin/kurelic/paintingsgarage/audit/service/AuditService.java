package eu.sanjin.kurelic.paintingsgarage.audit.service;

import eu.sanjin.kurelic.paintingsgarage.audit.entity.Audit;
import eu.sanjin.kurelic.paintingsgarage.audit.entity.AuditAction;
import eu.sanjin.kurelic.paintingsgarage.audit.mapper.AuditMapper;
import eu.sanjin.kurelic.paintingsgarage.audit.model.AuditModel;
import eu.sanjin.kurelic.paintingsgarage.audit.repository.AuditRepository;
import eu.sanjin.kurelic.paintingsgarage.photo.model.PhotoData;
import eu.sanjin.kurelic.paintingsgarage.security.model.UserDetailsModel;
import eu.sanjin.kurelic.paintingsgarage.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {

  private final AuditRepository repository;
  private final AuditMapper mapper;

  public List<AuditModel> getLatestAuditLogs() {
    return mapper.mapAuditListToAuditModelList(repository.findFirst10ByOrderByTimeDesc());
  }

  private void logAudit(AuditAction action, String subject, String object) {
    repository.saveAndFlush(
      Audit.builder()
        .time(LocalDateTime.now())
        .action(action)
        .subject(subject)
        .object(object)
        .build()
    );
  }

  private void logAudit(AuditAction action, String object) {
    var subject = (UserDetailsModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    logAudit(action, subject.getName(), object);
  }

  public void logPhotoChanges(PhotoData photo, AuditAction action) {
    logAudit(action, photo.path());
  }

  public void logAuthChanges(AuditAction action) {
    logAudit(action, null);
  }

  public void logUserChanges(User user, String userPlan) {
    logAudit(AuditAction.USER_CHANGE_PLAN, user.getName(), userPlan);
  }
}
