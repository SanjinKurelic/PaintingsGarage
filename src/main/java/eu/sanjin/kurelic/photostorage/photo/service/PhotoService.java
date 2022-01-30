package eu.sanjin.kurelic.photostorage.photo.service;

import eu.sanjin.kurelic.photostorage.photo.mapper.PhotoMapper;
import eu.sanjin.kurelic.photostorage.photo.model.PhotoData;
import eu.sanjin.kurelic.photostorage.photo.model.PhotoSize;
import eu.sanjin.kurelic.photostorage.photo.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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

  public List<PhotoData> findImages(List<Long> authors, List<Long> hashtags, PhotoSize size, LocalDateTime dateFrom, LocalDateTime dateTo) {
    return photoMapper.mapPhotoListToPhotoDataList(photoRepository.findAllBy(authors, hashtags, size, dateFrom, dateTo));
  }

  public void updatePhoto(Long photoId, String title, String description, Map<String, Long> hashtagList) {
    var photo = photoRepository.getById(photoId);

    photo.setTitle(title);
    photo.setDescription(description);
    //photo.setHashtags(); // TODO change (add new) hashtags

    photoRepository.saveAndFlush(photo);
  }

  public void deletePhoto(Long photoId) {
    photoRepository.deleteById(photoId);
  }
}
