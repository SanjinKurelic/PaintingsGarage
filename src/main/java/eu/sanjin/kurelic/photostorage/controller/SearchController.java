package eu.sanjin.kurelic.photostorage.controller;

import eu.sanjin.kurelic.photostorage.model.PhotoData;
import eu.sanjin.kurelic.photostorage.model.SearchModel;
import eu.sanjin.kurelic.photostorage.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

  private final SearchService searchService;

  @GetMapping("/image/latest")
  public ResponseEntity<List<PhotoData>> getLatestPhotoList() {
    return ResponseEntity.ok(searchService.getLatestPhotoList());
  }

  @GetMapping("/author/{authorName}")
  public ResponseEntity<List<SearchModel>> findAuthor(@PathVariable String authorName) {
    var authorList = searchService.findAuthor(authorName);

    if (Objects.isNull(authorList) || authorList.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(authorList);
  }

  @GetMapping("/hashtag/{name}")
  public ResponseEntity<List<SearchModel>> findHashTag(@PathVariable String name) {
    var hashTagList = searchService.findHashTag(name);

    if (Objects.isNull(hashTagList) || hashTagList.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(hashTagList);
  }

  @PostMapping("/image")
  public ResponseEntity<List<PhotoData>> findImages(@RequestBody List<SearchModel> searchModelList) {
    var images = searchService.findImages(searchModelList);

    if (Objects.isNull(images) || images.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(images);
  }
}
