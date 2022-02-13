package eu.sanjin.kurelic.paintingsgarage.audit.controller;

import eu.sanjin.kurelic.paintingsgarage.audit.model.AuditModel;
import eu.sanjin.kurelic.paintingsgarage.audit.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/audit")
public class AuditController {

  private final AuditService auditService;

  @GetMapping
  public ResponseEntity<List<AuditModel>> getAuditLogs() {
    return ResponseEntity.ok(auditService.getLatestAuditLogs());
  }
}
