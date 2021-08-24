package eu.sanjin.kurelic.photostorage.controller;

import eu.sanjin.kurelic.photostorage.model.PhotoData;
import eu.sanjin.kurelic.photostorage.model.SearchModel;
import eu.sanjin.kurelic.photostorage.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

  private final FileService fileService;

  @PostMapping("/upload")
  public ResponseEntity<PhotoData> uploadFile(
    @RequestParam("file") MultipartFile file,
    @RequestParam(value = "description", required = false) String description,
    @RequestParam(value = "hashTagList", required = false) List<SearchModel> hashTagList) {
    return ResponseEntity.ok(fileService.saveFile(file, description, hashTagList));
  }

  @GetMapping("/download/{fileName:.+}")
  public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
    return ResponseEntity.ok()
      .contentType(fileService.getMediaType(fileName))
      .body(fileService.loadFile(fileName));
  }
}
