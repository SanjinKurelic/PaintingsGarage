package eu.sanjin.kurelic.paintingsgarage.search.controller;

import eu.sanjin.kurelic.paintingsgarage.search.model.SearchResult;
import eu.sanjin.kurelic.paintingsgarage.hashtag.service.HashtagService;
import eu.sanjin.kurelic.paintingsgarage.photo.model.PhotoData;
import eu.sanjin.kurelic.paintingsgarage.photo.model.PhotoSize;
import eu.sanjin.kurelic.paintingsgarage.photo.service.PhotoService;
import eu.sanjin.kurelic.paintingsgarage.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {

  private final UserService userService;
  private final PhotoService photoService;
  private final HashtagService hashtagService;

  @GetMapping("/hashtag/{hashtagName}")
  public ResponseEntity<List<SearchResult>> findHashtag(@PathVariable String hashtagName) {
    return ResponseEntity.ok(hashtagService.findHashtag(hashtagName));
  }

  @GetMapping("/author/{authorName}")
  public ResponseEntity<List<SearchResult>> findAuthor(@PathVariable String authorName) {
    return ResponseEntity.ok(userService.findAuthor(authorName));
  }

  @GetMapping("/photo")
  public ResponseEntity<List<PhotoData>> findPhoto(
    @RequestParam(required = false) List<Long> authors,
    @RequestParam(required = false) List<Long> hashtags,
    @RequestParam(required = false) PhotoSize size,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo
  ) {
    return ResponseEntity.ok(photoService.findImages(authors, hashtags, size, dateFrom, dateTo));
  }
}
