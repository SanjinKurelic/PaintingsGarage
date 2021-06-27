package eu.sanjin.kurelic.photostorage.controller;

import eu.sanjin.kurelic.photostorage.model.PhotoData;
import eu.sanjin.kurelic.photostorage.model.SearchModel;
import eu.sanjin.kurelic.photostorage.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController("/api")
@RequiredArgsConstructor
public class FileController {

  private final FileService fileService;

  @PostMapping("/uploadFile")
  public ResponseEntity<PhotoData> uploadFile(
    @RequestParam("file") MultipartFile file,
    @RequestParam(value = "description", required = false) String description,
    @RequestParam(value = "hashTagList", required = false) List<SearchModel> hashTagList) {
    return ResponseEntity.ok(fileService.saveFile(file, description, hashTagList));
  }

  @GetMapping("/downloadFile/{fileName}.{extension}")
  public ResponseEntity<Resource> downloadFile(@PathVariable UUID fileName, @PathVariable String extension) {
    return ResponseEntity.ok()
      .contentType(MediaType.parseMediaType(extension))
      //.header(HttpHeaders.CONTENT_DISPOSITION)
      .body(fileService.loadFile(String.join(".", fileName.toString(), extension)));
  }

}
