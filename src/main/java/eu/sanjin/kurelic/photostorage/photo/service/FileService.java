package eu.sanjin.kurelic.photostorage.photo.service;

import eu.sanjin.kurelic.photostorage.photo.filter.ImageFilterType;
import eu.sanjin.kurelic.photostorage.photo.model.PhotoSize;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public interface FileService {

  String THUMBNAIL_PREFIX = "th";

  String saveFile(MultipartFile file);

  byte[] loadFile(String fileName, ImageFilterType filter, PhotoSize size);

  void deleteFile(String fileName);

  MediaType getMediaType(String fileName);

  default String getThumbnailPrefix(String fileName) {
    var extension = Objects.requireNonNull(StringUtils.getFilenameExtension(fileName));
    var file = StringUtils.stripFilenameExtension(fileName);
    return String.format("%s_%s.%s", file, THUMBNAIL_PREFIX, extension);
  }
}
