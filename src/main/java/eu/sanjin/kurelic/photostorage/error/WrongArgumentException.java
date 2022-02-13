package eu.sanjin.kurelic.photostorage.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WrongArgumentException extends RuntimeException implements MessageBasedException {

  @RequiredArgsConstructor
  @Getter
  public enum WrongArgumentMessage {
    WRONG_FILE_FORMAT("wrong.file.argument");

    private final String errorCode;
  }

  private final WrongArgumentMessage errorMessage;

  @Override
  public String getErrorCode() {
    return errorMessage.getErrorCode();
  }
}
