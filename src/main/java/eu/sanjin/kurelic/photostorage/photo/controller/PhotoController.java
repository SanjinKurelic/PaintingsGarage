package eu.sanjin.kurelic.photostorage.photo.controller;

import eu.sanjin.kurelic.photostorage.audit.aspect.LogPhotoUpload;
import eu.sanjin.kurelic.photostorage.photo.filter.ImageFilterType;
import eu.sanjin.kurelic.photostorage.photo.model.PhotoData;
import eu.sanjin.kurelic.photostorage.photo.model.PhotoSize;
import eu.sanjin.kurelic.photostorage.photo.service.FileService;
import eu.sanjin.kurelic.photostorage.photo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
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
  public ResponseEntity<byte[]> downloadFile(
    @PathVariable String fileName,
    @RequestParam(required = false) ImageFilterType filter,
    @RequestParam(required = false) PhotoSize size,
    @RequestParam(required = false) Boolean download
  ) {
    var content = fileService.loadFile(fileName, filter, size);

    if (Objects.isNull(content) || content.length == 0) {
      return ResponseEntity.notFound().build();
    }

    if (!photoService.isUserOwner(fileName)) {
      content = fileService.applyWatermark(content, fileName);
    }

    // Extra http headers
    var httpHeaders = new HttpHeaders();
    if (Boolean.TRUE.equals(download)) {
      httpHeaders.add(
        HttpHeaders.CONTENT_DISPOSITION,
        String.format("attachment; filename=\"%1$s\"; filename*=\"%1$s\"", fileName)
      );
    }

    return ResponseEntity.ok()
      .contentType(fileService.getMediaType(fileName))
      .contentLength(content.length)
      .headers(httpHeaders)
      .cacheControl(CacheControl.maxAge(3, TimeUnit.HOURS).mustRevalidate().noTransform())
      .body(content);
  }

  @PostMapping
  @LogPhotoUpload
  public ResponseEntity<PhotoData> addPhoto(@RequestParam("file") MultipartFile file, PhotoData photoData) {
    var filePath = fileService.saveFile(file);
    var thumbnailPath = fileService.getThumbnailPrefix(filePath);
    var fileSize = file.getSize();
    return ResponseEntity.ok(photoService.addPhoto(filePath, thumbnailPath, fileSize, photoData));
  }

  @PutMapping("/{photoId}")
  public void updatePhoto(@PathVariable Long photoId, PhotoData photoData) {
    photoService.updatePhoto(photoId, photoData);
  }

  @DeleteMapping("/{photoId}")
  public void deletePhoto(@PathVariable Long photoId) {
    var photo = photoService.getPhoto(photoId);
    photoService.deletePhoto(photoId);
    fileService.deleteFile(photo.path());
    fileService.deleteFile(photo.thumbnail());
  }
}
