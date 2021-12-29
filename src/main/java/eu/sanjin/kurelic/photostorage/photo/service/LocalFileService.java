package eu.sanjin.kurelic.photostorage.photo.service;

import eu.sanjin.kurelic.photostorage.common.exceptions.InternalServerError;
import eu.sanjin.kurelic.photostorage.common.exceptions.WrongArgumentException;
import eu.sanjin.kurelic.photostorage.photo.file.ImageStreamingDestinationFile;
import eu.sanjin.kurelic.photostorage.photo.file.ImageStreamingSourceFile;
import eu.sanjin.kurelic.photostorage.photo.config.FileStorageConfig;
import eu.sanjin.kurelic.photostorage.photo.entity.Photo;
import eu.sanjin.kurelic.photostorage.photo.mapper.PhotoMapper;
import eu.sanjin.kurelic.photostorage.photo.model.PhotoData;
import eu.sanjin.kurelic.photostorage.photo.repository.PhotoRepository;
import eu.sanjin.kurelic.photostorage.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocalFileService implements FileService {

  private final PhotoMapper photoMapper;
  private final FileStorageConfig config;
  private final PhotoRepository photoRepository;

  private static final String THUMBNAIL_PREFIX = "th";
  private static final int MEGABYTE = 1024 * 1024;

  @Override
  public PhotoData saveFile(MultipartFile file, String title, String description, Map<String, Long> hashtagList) {
    // Validate
    if (file.isEmpty()) {
      throw new WrongArgumentException(WrongArgumentException.WrongArgumentMessage.WRONG_FILE_FORMAT);
    }

    // Save to sftp
    var extension = validateExtension(file.getOriginalFilename());
    final var fileId = uploadFile(file, extension);

    // Save to database
    var author = new User();
    author.setId(1L);

    var photo = Photo.builder()
      .author(author) // TODO get current user
      .title(title)
      .description(description)
      //.hashtags(hashtags) // TODO if search model does not have id, store it as new hashtag
      .path(String.join(".", fileId, extension))
      .thumbnail(String.format("%s_%s.%s", fileId, THUMBNAIL_PREFIX, extension))
      .size((int) (file.getSize() / MEGABYTE))
      .uploaded(LocalDateTime.now())
      .build();

    return photoMapper.mapPhotoToPhotoData(photoRepository.save(photo));
  }

  private String uploadFile(MultipartFile multipartFile, String extension) {
    var fileId = UUID.randomUUID().toString();
    var fileName = String.join(".", fileId, extension);
    var thumbnailName = String.format("%s_%s.%s", fileId, THUMBNAIL_PREFIX, extension);

    try {
      try (var sftp = getSftpClient(new SSHClient())) {
        var sourceFile = new ImageStreamingSourceFile(multipartFile);
        sftp.put(sourceFile, appendRemotePath(fileName));
        // TODO store resize image for thumbnail
        var resizedImage = multipartFile.getInputStream();
        sftp.put(new ImageStreamingSourceFile(multipartFile, resizedImage), appendRemotePath(thumbnailName));
        resizedImage.close();
        sourceFile.close();
      }
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new InternalServerError(InternalServerError.InternalServerErrorMessage.ERROR_STORAGE_SPACE);
    }

    return fileId;
  }

  @Override
  public byte[] loadFile(String fileName) {
    var path = appendRemotePath(fileName);
    byte[] content = null;

    try (var remoteFile = getSftpClient(new SSHClient())) {
      var destinationFile = new ImageStreamingDestinationFile();

      remoteFile.getFileTransfer().download(path, destinationFile);

      content = ((ByteArrayOutputStream) destinationFile.getOutputStream()).toByteArray();

      destinationFile.close();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }

    return content;
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
