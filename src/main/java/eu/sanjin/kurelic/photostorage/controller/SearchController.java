package eu.sanjin.kurelic.photostorage.controller;

import eu.sanjin.kurelic.photostorage.model.PhotoData;
import eu.sanjin.kurelic.photostorage.model.SearchModel;
import eu.sanjin.kurelic.photostorage.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchController {

  private final SearchService searchService;

  @GetMapping("findAuthor/{authorName}")
  public List<SearchModel> findAuthor(String authorName) {
    return searchService.findAuthor(authorName);
  }

  @GetMapping("findHashTag/{name}")
  public List<SearchModel> findHashTag(String name) {
    return searchService.findHashTag(name);
  }

  @GetMapping("findImages")
  public List<PhotoData> findImages(List<SearchModel> searchModelList) {
    return searchService.findImages(searchModelList);
  }

}
