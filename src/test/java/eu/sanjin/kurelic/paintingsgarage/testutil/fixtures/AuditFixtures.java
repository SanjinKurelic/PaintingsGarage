package eu.sanjin.kurelic.paintingsgarage.testutil.fixtures;

import java.time.LocalDateTime;

import eu.sanjin.kurelic.paintingsgarage.audit.entity.AuditAction;

import eu.sanjin.kurelic.paintingsgarage.audit.entity.Audit;

public class AuditFixtures {

  public static final LocalDateTime NOW = LocalDateTime.now();

  public static Audit someAudit(AuditAction auditAction) {
    var audit = new Audit();

    audit.setId(99L);
    audit.setAction(auditAction);
    audit.setTime(NOW);
    audit.setSubject("Some subject");
    audit.setObject("Some event");

    return audit;
  }
}
