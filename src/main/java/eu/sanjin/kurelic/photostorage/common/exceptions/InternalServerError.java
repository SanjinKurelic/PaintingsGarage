package eu.sanjin.kurelic.photostorage.common.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InternalServerError extends RuntimeException implements MessageBasedException {

  @Getter
  @RequiredArgsConstructor
  public enum InternalServerErrorMessage {
    ERROR_STORAGE_SPACE("no.storage.space");

    private final String errorCode;
  }

  private final InternalServerErrorMessage errorMessage;

  @Override
  public String getErrorCode() {
    return errorMessage.getErrorCode();
  }
}
