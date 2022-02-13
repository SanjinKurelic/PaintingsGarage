package eu.sanjin.kurelic.paintingsgarage.photo.file;

import net.schmizz.sshj.xfer.InMemorySourceFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ImageStreamingSourceFile extends InMemorySourceFile {

  private final MultipartFile multipartFile;
  private final InputStream inputStream;

  public ImageStreamingSourceFile(MultipartFile multipartFile) {
    this.multipartFile = multipartFile;
    this.inputStream = null;
  }

  public ImageStreamingSourceFile(MultipartFile multipartFile, InputStream inputStream) {
    this.multipartFile = multipartFile;
    this.inputStream = inputStream;
  }

  @Override
  public String getName() {
    return multipartFile.getOriginalFilename();
  }

  @Override
  public long getLength() {
    return multipartFile.getSize();
  }

  @Override
  public InputStream getInputStream() throws IOException {
    return Objects.nonNull(inputStream) ? inputStream : multipartFile.getInputStream();
  }

  public void close() throws IOException {
    if (Objects.nonNull(inputStream)) {
      inputStream.close();
    }
  }
}
