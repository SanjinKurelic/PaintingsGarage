package eu.sanjin.kurelic.photostorage.service;

import eu.sanjin.kurelic.photostorage.model.PhotoData;
import eu.sanjin.kurelic.photostorage.model.SearchModel;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

  PhotoData saveFile(MultipartFile file, String description, List<SearchModel> hashTagList);

  Resource loadFile(String fileName);

}
