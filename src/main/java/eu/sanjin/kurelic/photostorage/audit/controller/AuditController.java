package eu.sanjin.kurelic.photostorage.audit.controller;

import eu.sanjin.kurelic.photostorage.audit.model.AuditModel;
import eu.sanjin.kurelic.photostorage.audit.service.AuditService;
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

  @GetMapping("/latest")
  public ResponseEntity<List<AuditModel>> getLatestAuditLogs() {
    return ResponseEntity.ok(auditService.getLatestAuditLogs());
  }
}
