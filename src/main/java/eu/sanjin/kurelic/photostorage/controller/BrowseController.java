package eu.sanjin.kurelic.photostorage.controller;

import eu.sanjin.kurelic.photostorage.model.PhotoData;
import eu.sanjin.kurelic.photostorage.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BrowseController {

  private final PhotoService photoService;

  @GetMapping("getLatestPhotoList")
  public List<PhotoData> getLatestPhotoList() {
    return photoService.getLatestPhotoList();
  }

}
