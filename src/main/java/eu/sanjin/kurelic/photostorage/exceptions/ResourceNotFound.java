package eu.sanjin.kurelic.photostorage.exceptions;

public class ResourceNotFound extends RuntimeException implements MessageBasedException {

  private static final String ERROR_CODE = "resource.not.found";

  @Override
  public String getErrorCode() {
    return ERROR_CODE;
  }
}
