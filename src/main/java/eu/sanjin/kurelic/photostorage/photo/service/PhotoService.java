package eu.sanjin.kurelic.photostorage.photo.service;

import eu.sanjin.kurelic.photostorage.hashtag.service.HashtagService;
import eu.sanjin.kurelic.photostorage.photo.entity.Photo;
import eu.sanjin.kurelic.photostorage.photo.mapper.PhotoMapper;
import eu.sanjin.kurelic.photostorage.photo.model.PhotoData;
import eu.sanjin.kurelic.photostorage.photo.model.PhotoSize;
import eu.sanjin.kurelic.photostorage.photo.repository.PhotoRepository;
import eu.sanjin.kurelic.photostorage.security.model.UserDetailsModel;
import eu.sanjin.kurelic.photostorage.user.entity.User;
import eu.sanjin.kurelic.photostorage.user.entity.UserRole;
import eu.sanjin.kurelic.photostorage.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PhotoService {

  private static final int MEGABYTE = 1024 * 1024;

  private final PhotoRepository photoRepository;
  private final HashtagService hashtagService;
  private final PhotoMapper photoMapper;
  private final UserService userService;

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

  public PhotoData getPhoto(Long photoId) {
    return photoMapper.mapPhotoToPhotoData(photoRepository.getById(photoId));
  }

  public PhotoData addPhoto(String photoPath, String thumbnailPath, long fileSize, PhotoData photoData) {
    var principal = (UserDetailsModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    var photo = Photo.builder()
      .author(userService.getUser(principal.getId()))
      .title(photoData.title())
      .description(photoData.description())
      .hashtags(hashtagService.getOrCreate(photoData.hashtags()))
      .path(photoPath)
      .thumbnail(thumbnailPath)
      .size((int) (fileSize / MEGABYTE))
      .uploaded(LocalDateTime.now())
      .digitalPrice(photoData.digitalPrice())
      .paintingPrice(photoData.paintingPrice())
      .build();

    return photoMapper.mapPhotoToPhotoData(photoRepository.saveAndFlush(photo));
  }

  public void updatePhoto(Long photoId, PhotoData photoData) {
    var photo = photoRepository.findById(photoId).orElseThrow();

    photo.setTitle(photoData.title());
    photo.setDescription(photoData.description());
    photo.setDigitalPrice(photoData.digitalPrice());
    photo.setPaintingPrice(photoData.paintingPrice());
    photo.setHashtags(hashtagService.getOrCreate(photoData.hashtags()));

    photoRepository.saveAndFlush(photo);
  }

  public void deletePhoto(Long photoId) {
    // Check if user is the owner of a photo
    var photo = photoRepository.getById(photoId);
    var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (!(principal instanceof UserDetailsModel userDetailsModel) || !userDetailsModel.getId().equals(photo.getAuthor().getId())) {
      throw new RuntimeException("User not the owner of a image");
    }
    photoRepository.deleteById(photoId);
  }

  public void buyPhoto(Long photoId, User user) {
    var photo = photoRepository.getById(photoId);

    photo.getOwners().add(user);

    photoRepository.saveAndFlush(photo);
  }

  public boolean isUserOwner(String photoPath) {
    var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    var photo = photoRepository.getByPath(photoPath);

    if (Objects.isNull(principal) || !(principal instanceof UserDetailsModel userDetailsModel)) {
      return false;
    }

    if (userDetailsModel.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(UserRole.ROLE_ADMIN.name()::equals)) {
      return true;
    }

    return photo.getAuthor().getId().equals(userDetailsModel.getId()) ||
      photo.getOwners().stream().anyMatch(o -> o.getId().equals(userDetailsModel.getId()));
  }
}
