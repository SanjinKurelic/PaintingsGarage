package eu.sanjin.kurelic.photostorage.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PhotoData {

  private String path;
  private String thumbnail;
  private String description;
  private LocalDateTime uploaded;
  private String author;
  private List<String> hashTags;
  private PhotoOwnershipType ownershipType;

}
