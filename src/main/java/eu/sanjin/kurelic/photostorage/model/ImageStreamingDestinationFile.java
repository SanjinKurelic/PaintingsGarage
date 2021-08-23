package eu.sanjin.kurelic.photostorage.model;

import net.schmizz.sshj.xfer.InMemoryDestFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageStreamingDestinationFile extends InMemoryDestFile {

  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

  @Override
  public OutputStream getOutputStream() throws IOException {
    return outputStream;
  }

  public void close() throws IOException {
    outputStream.close();
  }
}
