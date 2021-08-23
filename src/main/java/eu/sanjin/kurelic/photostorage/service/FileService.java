package eu.sanjin.kurelic.photostorage.service;

import eu.sanjin.kurelic.photostorage.model.PhotoData;
import eu.sanjin.kurelic.photostorage.model.SearchModel;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

  PhotoData saveFile(MultipartFile file, String description, List<SearchModel> hashTagList);

  byte[] loadFile(String fileName);

  MediaType getMediaType(String fileName);

}
