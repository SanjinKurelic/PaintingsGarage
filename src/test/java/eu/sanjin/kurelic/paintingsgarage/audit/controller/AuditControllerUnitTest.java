package eu.sanjin.kurelic.paintingsgarage.audit.controller;

import eu.sanjin.kurelic.paintingsgarage.audit.model.AuditModel;
import eu.sanjin.kurelic.paintingsgarage.audit.service.AuditService;
import eu.sanjin.kurelic.paintingsgarage.testutil.type.UnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@UnitTest
class AuditControllerUnitTest {

  @Mock
  private AuditService auditService;

  @InjectMocks
  private AuditController auditController;

  @Test
  void shouldGetAuditLogs() {
    // Given
    var auditLogs = List.of(mock(AuditModel.class));

    when(auditService.getLatestAuditLogs()).thenReturn(auditLogs);

    // When
    var response = auditController.getAuditLogs();

    // Then
    assertThat(response).isInstanceOf(ResponseEntity.class).isNotNull();
    verify(auditService, times(1)).getLatestAuditLogs();
  }
}