package eu.sanjin.kurelic.photostorage.hashtag.controller;

import eu.sanjin.kurelic.photostorage.common.model.SearchResult;
import eu.sanjin.kurelic.photostorage.hashtag.service.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hashtag")
public class HashtagController {

  private final HashtagService hashtagService;

  @GetMapping("/find/{hashtagName}")
  public ResponseEntity<List<SearchResult>> findHashtag(@PathVariable String hashtagName) {
    return ResponseEntity.ok(hashtagService.findHashtag(hashtagName));
  }
}
