package eu.sanjin.kurelic.photostorage.photo.controller;

import eu.sanjin.kurelic.photostorage.audit.aspect.LogPhotoUpload;
import eu.sanjin.kurelic.photostorage.photo.model.PhotoData;
import eu.sanjin.kurelic.photostorage.photo.service.FileService;
import eu.sanjin.kurelic.photostorage.photo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/photo")
@RequiredArgsConstructor
public class PhotoController {

  private final PhotoService photoService;
  private final FileService fileService;

  @GetMapping
  public ResponseEntity<List<PhotoData>> getPhotoList() {
    return ResponseEntity.ok(photoService.getLatestPhotoList());
  }

  @GetMapping("/{fileName:.+}")
  public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
    var content = fileService.loadFile(fileName);

    if (Objects.isNull(content) || content.length == 0) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok()
      .contentType(fileService.getMediaType(fileName))
      .cacheControl(CacheControl.maxAge(3, TimeUnit.HOURS).mustRevalidate().noTransform())
      .body(content);
  }

  @PostMapping
  @LogPhotoUpload
  public ResponseEntity<PhotoData> addPhoto(
    @RequestParam("file") MultipartFile file,
    @RequestParam(value = "title") String title,
    @RequestParam(value = "description") String description,
    @RequestParam(value = "hashtagList") Map<String, Long> hashtagList) {
    return ResponseEntity.ok(fileService.saveFile(file, title, description, hashtagList));
  }

  @PutMapping("/{photoId}")
  public void updatePhoto(
    @PathVariable Long photoId,
    @RequestParam(value = "title") String title,
    @RequestParam(value = "description") String description,
    @RequestParam(value = "hashtagList") Map<String, Long> hashtagList
  ) {
    photoService.updatePhoto(photoId, title, description, hashtagList);
  }

  @DeleteMapping("/{photoId}")
  public void deletePhoto(@PathVariable Long photoId) {
    photoService.deletePhoto(photoId);
    // TODO also delete file
  }
}
