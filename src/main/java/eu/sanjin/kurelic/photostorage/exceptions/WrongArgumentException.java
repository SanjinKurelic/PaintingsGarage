package eu.sanjin.kurelic.photostorage.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WrongArgumentException extends RuntimeException {

  private final ErrorCode errorCode;

}
