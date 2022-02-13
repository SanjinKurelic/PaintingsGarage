package eu.sanjin.kurelic.paintingsgarage.error;

public class InternalServerError extends RuntimeException {

  public InternalServerError(String message) {
    super(message);
  }

  public InternalServerError(Throwable throwable) {
    super(throwable);
  }
}
