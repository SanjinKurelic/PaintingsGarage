package eu.sanjin.kurelic.photostorage.photo.controller;

import eu.sanjin.kurelic.photostorage.photo.model.PhotoData;
import eu.sanjin.kurelic.photostorage.photo.model.PhotoSize;
import eu.sanjin.kurelic.photostorage.photo.service.FileService;
import eu.sanjin.kurelic.photostorage.photo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
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

  @PostMapping("/upload")
  public ResponseEntity<PhotoData> uploadFile(
    @RequestParam("file") MultipartFile file,
    @RequestParam(value = "title") String title,
    @RequestParam(value = "description") String description,
    @RequestParam(value = "hashtagList") Map<String, Long> hashtagList) {
    return ResponseEntity.ok(fileService.saveFile(file, title, description, hashtagList));
  }

  @GetMapping("/download/{fileName:.+}")
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

  @GetMapping("/latest")
  public ResponseEntity<List<PhotoData>> getLatestPhotoList() {
    return ResponseEntity.ok(photoService.getLatestPhotoList());
  }

  @GetMapping("/find")
  public ResponseEntity<List<PhotoData>> findImages(
    @RequestParam(required = false) List<Long> authors,
    @RequestParam(required = false) List<Long> hashtags,
    @RequestParam(required = false) PhotoSize size,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo
  ) {
    return ResponseEntity.ok(photoService.findImages(authors, hashtags, size, dateFrom, dateTo));
  }
}
