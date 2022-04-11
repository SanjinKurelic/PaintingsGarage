package eu.sanjin.kurelic.paintingsgarage.audit.mapper;

import eu.sanjin.kurelic.paintingsgarage.audit.entity.AuditAction;
import eu.sanjin.kurelic.paintingsgarage.audit.model.AuditModel;
import eu.sanjin.kurelic.paintingsgarage.audit.model.AuditType;
import eu.sanjin.kurelic.paintingsgarage.testutil.fixtures.AuditFixtures;
import eu.sanjin.kurelic.paintingsgarage.testutil.type.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

@UnitTest
class AuditMapperUnitTest {

  @Mock
  private MessageSource messageSource;

  @InjectMocks
  private AuditMapper auditMapper;

  @ParameterizedTest
  @MethodSource("provideAuditActions")
  void shouldMapAuditToAuditModel(AuditAction auditAction, AuditType auditType) {
    // Given
    var audit = AuditFixtures.someAudit(auditAction);

    when(messageSource.getMessage(eq(String.format("audit.action.%s", auditAction.name().toLowerCase())), isNull(), any())).thenReturn("Some action");

    when(messageSource.getMessage(
      eq("audit.latest.message.format"),
      eq(new Object[]{
        audit.getSubject(),
        "Some action",
        audit.getObject()
      }),
      any()
    )).thenReturn("Some message");

    // When
    var result = auditMapper.mapAuditToAuditModel(audit);

    // Then
    assertAuditModel(result, auditType);
  }

  @Test
  void shouldMapAuditListToAuditModelList() {
    // Given
    var auditList = List.of(AuditFixtures.someAudit(AuditAction.PICTURE_CHANGE));

    when(messageSource.getMessage(any(), any(), any())).thenReturn("Some message");

    // When
    var result = auditMapper.mapAuditListToAuditModelList(auditList);

    // Then
    assertAuditModel(result.get(0), AuditType.IMAGE_TYPE);
  }

  private void assertAuditModel(AuditModel auditModel, AuditType auditType) {
    assertThat(auditModel.message()).isEqualTo("Some message");
    assertThat(auditModel.type()).isEqualTo(auditType);
    assertThat(auditModel.time()).isEqualTo(AuditFixtures.NOW);
  }

  private static Stream<Arguments> provideAuditActions() {
    return Stream.of(
      Arguments.of(AuditAction.PICTURE_BOUGHT, AuditType.IMAGE_TYPE),
      Arguments.of(AuditAction.PICTURE_CHANGE, AuditType.IMAGE_TYPE),
      Arguments.of(AuditAction.PICTURE_UPLOAD, AuditType.IMAGE_TYPE),
      Arguments.of(AuditAction.PICTURE_DELETE, AuditType.IMAGE_TYPE),
      Arguments.of(AuditAction.USER_CHANGE_PLAN, AuditType.USER_TYPE),
      Arguments.of(AuditAction.USER_LOGIN, AuditType.AUTH_TYPE),
      Arguments.of(AuditAction.USER_REGISTER, AuditType.AUTH_TYPE)
    );
  }
}