package eu.sanjin.kurelic.photostorage.audit.mapper;

import eu.sanjin.kurelic.photostorage.audit.entity.Audit;
import eu.sanjin.kurelic.photostorage.audit.entity.AuditAction;
import eu.sanjin.kurelic.photostorage.audit.model.AuditModel;
import eu.sanjin.kurelic.photostorage.audit.model.AuditType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuditMapper {

  private static final String AUDIT_MESSAGE_FORMAT = "audit.latest.message.format";
  private static final String AUDIT_ACTION_MESSAGE_FORMAT = "audit.action.%s";
  private final MessageSource messageSource;

  private String mapAuditActionToString(AuditAction action) {
    return messageSource.getMessage(
      String.format(AUDIT_ACTION_MESSAGE_FORMAT, action.name().toLowerCase()),
      null,
      LocaleContextHolder.getLocale()
    );
  }

  private AuditType mapAuditActionToAuditType(AuditAction action) {
    return switch (action) {
      case USER_REGISTER, USER_LOGIN -> AuditType.AUTH_TYPE;
      case USER_CHANGE_PLAN -> AuditType.USER_TYPE;
      case PICTURE_BOUGHT, PICTURE_UPLOAD, PICTURE_DELETE, PICTURE_CHANGE -> AuditType.IMAGE_TYPE;
    };
  }

  public AuditModel mapAuditToAuditModel(Audit audit) {
    return new AuditModel(
      messageSource.getMessage(
        AUDIT_MESSAGE_FORMAT,
        new Object[]{
          audit.getSubject(),
          mapAuditActionToString(audit.getAction()),
          Objects.nonNull(audit.getObject()) ? audit.getObject() : ""
        },
        LocaleContextHolder.getLocale()
      ),
      mapAuditActionToAuditType(audit.getAction()),
      audit.getTime()
    );
  }

  public List<AuditModel> mapAuditListToAuditModelList(List<Audit> auditList) {
    return auditList.stream().map(this::mapAuditToAuditModel).toList();
  }
}
