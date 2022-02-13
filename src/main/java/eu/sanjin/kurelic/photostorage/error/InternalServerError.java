package eu.sanjin.kurelic.photostorage.error;

public class InternalServerError extends RuntimeException {

  public InternalServerError(String message) {
    super(message);
  }

  public InternalServerError(Throwable throwable) {
    super(throwable);
  }
}
