package eu.sanjin.kurelic.paintingsgarage.photo.service;

import eu.sanjin.kurelic.paintingsgarage.photo.filter.ImageFilterType;
import eu.sanjin.kurelic.paintingsgarage.photo.model.PhotoSize;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public interface FileService {

  String THUMBNAIL_PREFIX = "th_";

  String saveFile(MultipartFile file);

  byte[] loadFile(String fileName, ImageFilterType filter, PhotoSize size);

  byte[] applyWatermark(byte[] image, String fileName);

  void deleteFile(String fileName);

  MediaType getMediaType(String fileName);

  default String getThumbnailPrefix(String fileName) {
    var extension = Objects.requireNonNull(StringUtils.getFilenameExtension(fileName));
    var file = StringUtils.stripFilenameExtension(fileName);
    return String.format("%s%s.%s", THUMBNAIL_PREFIX, file, extension);
  }
}
