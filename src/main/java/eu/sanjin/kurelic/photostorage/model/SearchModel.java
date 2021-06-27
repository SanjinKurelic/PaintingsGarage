package eu.sanjin.kurelic.photostorage.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchModel {

  private Long id;
  private String value;
  private SearchType type;

}
