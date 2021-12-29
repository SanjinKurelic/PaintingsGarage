package eu.sanjin.kurelic.photostorage.modules.photo.service;

import eu.sanjin.kurelic.photostorage.modules.photo.model.PhotoData;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileService {

  PhotoData saveFile(MultipartFile file, String title, String description, Map<String, Long> hashtagList);

  byte[] loadFile(String fileName);

  MediaType getMediaType(String fileName);
}
