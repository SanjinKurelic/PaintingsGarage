package eu.sanjin.kurelic.photostorage.modules.photo.service;

import eu.sanjin.kurelic.photostorage.modules.photo.mapper.PhotoMapper;
import eu.sanjin.kurelic.photostorage.modules.photo.model.PhotoData;
import eu.sanjin.kurelic.photostorage.modules.photo.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PhotoService {

  private final PhotoRepository photoRepository;
  private final PhotoMapper photoMapper;

  public List<PhotoData> getLatestPhotoList() {
    var photoList = photoMapper.mapPhotoListToPhotoDataList(photoRepository.findFirst9ByOrderByUploadedDesc());

    if (Objects.isNull(photoList)) {
      photoList = List.of();
    }

    return photoList;
  }

  public List<PhotoData> findImages(List<Long> authors, List<Long> hashtags, Integer size, LocalDateTime dateFrom, LocalDateTime dateTo) {
    return photoMapper.mapPhotoListToPhotoDataList(photoRepository.findAllBy(authors, hashtags, size, dateFrom, dateTo));
  }
}