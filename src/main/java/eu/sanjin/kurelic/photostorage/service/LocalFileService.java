package eu.sanjin.kurelic.photostorage.service;

import eu.sanjin.kurelic.photostorage.config.FileStorageConfig;
import eu.sanjin.kurelic.photostorage.data.entity.HashTag;
import eu.sanjin.kurelic.photostorage.data.entity.Photo;
import eu.sanjin.kurelic.photostorage.data.entity.User;
import eu.sanjin.kurelic.photostorage.data.repository.PhotoRepository;
import eu.sanjin.kurelic.photostorage.exceptions.InternalServerError;
import eu.sanjin.kurelic.photostorage.exceptions.ResourceNotFound;
import eu.sanjin.kurelic.photostorage.exceptions.WrongArgumentException;
import eu.sanjin.kurelic.photostorage.mapper.PhotoMapper;
import eu.sanjin.kurelic.photostorage.model.PhotoData;
import eu.sanjin.kurelic.photostorage.model.SearchModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocalFileService implements FileService {

  private final PhotoMapper photoMapper;
  private final FileStorageConfig config;
  private final PhotoRepository photoRepository;

  private static final int MEGABYTE = 1024 * 1024;

  private Path uploadDirectory;

  @PostConstruct
  private void postConstruct() throws IOException {
    uploadDirectory = Paths.get(config.getDirectory()).toAbsolutePath().normalize();

    Files.createDirectory(uploadDirectory);
  }

  public PhotoData saveFile(MultipartFile file, String description, List<SearchModel> hashTagList) {
    final var fileName = uploadFile(file);
    final var author = new User(); // TODO get current user
    final var hashTags = new ArrayList<HashTag>(); // TODO if search model does not have id, store it as new hashtag

    var photo = Photo.builder()
      .author(author)
      .description(description)
      .hashTags(hashTags)
      .path(fileName)
      .thumbnail(generateThumbnailName(fileName))
      .size((int) (file.getSize() / MEGABYTE))
      .uploaded(LocalDateTime.now())
      .build();

    return photoMapper.mapPhotoToPhotoData(photoRepository.save(photo));
  }

  private String uploadFile(MultipartFile multipartFile) {
    var extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
    if (multipartFile.isEmpty() && !config.getExtensionList().contains(extension)) {
      throw new WrongArgumentException(WrongArgumentException.WrongArgumentMessage.WRONG_FILE_FORMAT);
    }

    var uuid = UUID.randomUUID().toString();
    var fileName = String.join(".", uuid, extension);

    try {
      Files.copy(multipartFile.getInputStream(), uploadDirectory.resolve(fileName));
      // TODO store resized thumbnail
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new InternalServerError(InternalServerError.InternalServerErrorMessage.ERROR_STORAGE_SPACE);
    }

    return fileName;
  }

  @Override
  public Resource loadFile(String fileName) {
    var path = uploadDirectory.resolve(fileName).normalize();
    // TODO check owner
    try {
      var resource = new UrlResource(path.toUri());
      if (resource.isFile()) {
        return resource;
      } else {
        throw new ResourceNotFound();
      }
    } catch (MalformedURLException e) {
      log.error(e.getMessage(), e);
      throw new ResourceNotFound();
    }
  }

  private String generateThumbnailName(final String fileName) {
    var extension = StringUtils.getFilenameExtension(fileName);
    var file = StringUtils.stripFilenameExtension(fileName);
    return String.join(".", String.join("_", file, config.getThumbnailPrefix()), extension);
  }

}
