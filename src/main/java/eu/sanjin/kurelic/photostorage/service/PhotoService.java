package eu.sanjin.kurelic.photostorage.service;

import eu.sanjin.kurelic.photostorage.data.repository.PhotoRepository;
import eu.sanjin.kurelic.photostorage.mapper.PhotoMapper;
import eu.sanjin.kurelic.photostorage.model.PhotoData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PhotoService {

  private final PhotoRepository repository;
  private final PhotoMapper mapper;

  public List<PhotoData> getLatestPhotoList() {
    var photoList = mapper.mapPhotoListToPhotoDataList(repository.findFirst10ByOrderByUploadedDesc());

    if (Objects.isNull(photoList)) {
      photoList = List.of();
    }

    return photoList;
  }

}
