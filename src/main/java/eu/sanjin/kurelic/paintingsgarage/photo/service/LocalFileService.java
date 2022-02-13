package eu.sanjin.kurelic.paintingsgarage.photo.service;

import eu.sanjin.kurelic.paintingsgarage.error.InternalServerError;
import eu.sanjin.kurelic.paintingsgarage.error.WrongArgumentException;
import eu.sanjin.kurelic.paintingsgarage.photo.config.FileStorageConfig;
import eu.sanjin.kurelic.paintingsgarage.photo.file.ImageStreamingDestinationFile;
import eu.sanjin.kurelic.paintingsgarage.photo.file.ImageStreamingSourceFile;
import eu.sanjin.kurelic.paintingsgarage.photo.filter.ImageFilterType;
import eu.sanjin.kurelic.paintingsgarage.photo.model.PhotoSize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.filters.Caption;
import net.coobird.thumbnailator.geometry.Positions;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocalFileService implements FileService {

  private final FileStorageConfig config;

  @Override
  public String saveFile(MultipartFile file) {
    // Validate
    if (file.isEmpty()) {
      throw new WrongArgumentException(WrongArgumentException.WrongArgumentMessage.WRONG_FILE_FORMAT);
    }
    // Validate extension
    var extension = validateExtension(file.getOriginalFilename());

    // Save to sftp
    return uploadFile(file, extension);
  }

  private String uploadFile(MultipartFile multipartFile, String extension) {
    var fileName = String.join(".", UUID.randomUUID().toString(), extension);
    var thumbnailName = getThumbnailPrefix(fileName);

    try {
      try (var sftp = getSftpClient(new SSHClient())) {
        // Save file
        var sourceFile = new ImageStreamingSourceFile(multipartFile);
        sftp.put(sourceFile, appendRemotePath(fileName));
        // Save thumbnail
        var resizedImage = new ByteArrayInputStream(changeSize(multipartFile.getBytes(), extension));
        sftp.put(new ImageStreamingSourceFile(multipartFile, resizedImage), appendRemotePath(thumbnailName));

        resizedImage.close();
        sourceFile.close();
      }
    } catch (IOException e) {
      throw new InternalServerError(e);
    }

    return fileName;
  }

  @Override
  public byte[] loadFile(String fileName, ImageFilterType filter, PhotoSize size) {
    var path = appendRemotePath(fileName);
    var extension = Objects.requireNonNull(StringUtils.getFilenameExtension(fileName));
    byte[] content = null;

    try (var remoteFile = getSftpClient(new SSHClient())) {
      var destinationFile = new ImageStreamingDestinationFile();

      remoteFile.getFileTransfer().download(path, destinationFile);

      content = ((ByteArrayOutputStream) destinationFile.getOutputStream()).toByteArray();

      // Apply filters
      if (Objects.nonNull(filter)) {
        content = applyFilters(content, filter, extension);
      }
      // Change size
      if (Objects.nonNull(size) && PhotoSize.SMALL.equals(size)) {
        content = changeSize(content, extension);
      }

      destinationFile.close();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }

    return content;
  }

  @Override
  public byte[] applyWatermark(byte[] image, String fileName) {
    // Don't add watermark to thumbnails
    if (fileName.startsWith(FileService.THUMBNAIL_PREFIX)) {
      return image;
    }
    try {
      var extension = Objects.requireNonNull(StringUtils.getFilenameExtension(fileName));
      var bufferedImage = ImageIO.read(new ByteArrayInputStream(image));
      var watermarkedImage = new ByteArrayOutputStream(image.length);

      var text = "Paintings Garage";
      var font = new Font("Monospaced", Font.BOLD, 60);
      var filter = new Caption(text, font, Color.WHITE, 0.5f, Positions.CENTER, 0);

      ImageIO.write(filter.apply(bufferedImage), extension, watermarkedImage);
      watermarkedImage.flush();

      return watermarkedImage.toByteArray();
    } catch (IOException e) {
      throw new InternalServerError(e);
    }
  }

  private byte[] changeSize(byte[] content, String extension) throws IOException {
    var bufferedImage = ImageIO.read(new ByteArrayInputStream(content));
    var resizedImage = new ByteArrayOutputStream(content.length);

    int newWidth = (bufferedImage.getWidth() / 2);
    int newHeight = (bufferedImage.getHeight() / 2);
    Thumbnails.of(bufferedImage).size(newWidth, newHeight).outputFormat(extension).toOutputStream(resizedImage);

    return resizedImage.toByteArray();
  }

  private byte[] applyFilters(byte[] content, ImageFilterType filter, String extension) throws IOException {
    var bufferedImage = ImageIO.read(new ByteArrayInputStream(content));
    var filteredImage = new ByteArrayOutputStream(content.length);

    ImageIO.write(filter.applyFilter(bufferedImage), extension, filteredImage);

    return filteredImage.toByteArray();
  }

  @Override
  public void deleteFile(String fileName) {
    var path = appendRemotePath(fileName);
    try {
      try (var sftp = getSftpClient(new SSHClient())) {
        sftp.rm(path);
      }
    } catch (IOException e) {
      throw new InternalServerError(e);
    }
  }

  @Override
  public MediaType getMediaType(String fileName) {
    var extension = validateExtension(fileName);

    return MediaType.parseMediaType("image/" + extension);
  }

  private String validateExtension(String fileName) {
    var extension = StringUtils.getFilenameExtension(fileName);

    if (!config.getExtensionList().contains(extension)) {
      throw new WrongArgumentException(WrongArgumentException.WrongArgumentMessage.WRONG_FILE_FORMAT);
    }

    return extension;
  }

  private String appendRemotePath(String fileName) {
    return config.getDirectory() + "/" + fileName;
  }

  private SFTPClient getSftpClient(SSHClient client) throws IOException {
    client.addHostKeyVerifier(new PromiscuousVerifier());
    client.connect(config.getHost(), config.getPort());
    client.authPassword(config.getUsername(), config.getPassword());

    return client.newSFTPClient();
  }
}
