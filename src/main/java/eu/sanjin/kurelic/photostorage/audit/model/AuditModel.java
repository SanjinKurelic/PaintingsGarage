package eu.sanjin.kurelic.photostorage.audit.model;

import java.time.LocalDateTime;

public record AuditModel(String message, AuditType type, LocalDateTime time) {
}
