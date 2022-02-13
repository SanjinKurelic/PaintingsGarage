package eu.sanjin.kurelic.paintingsgarage.photo.file;

import net.schmizz.sshj.xfer.InMemoryDestFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageStreamingDestinationFile extends InMemoryDestFile {

  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

  @Override
  public OutputStream getOutputStream() {
    return outputStream;
  }

  public void close() throws IOException {
    outputStream.close();
  }
}
