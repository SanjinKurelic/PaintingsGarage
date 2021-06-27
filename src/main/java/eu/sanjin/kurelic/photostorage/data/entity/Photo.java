package eu.sanjin.kurelic.photostorage.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "photo")
public class Photo implements Serializable {

  @Id
  private Long id;

  private String path;

  private String thumbnail;

  private String description;

  private Integer size;

  private LocalDateTime uploaded;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User author; //NOSONAR

  // We always require hash tags with photo entity
  @ManyToMany(mappedBy = "photoList", fetch = FetchType.EAGER)
  private List<HashTag> hashTags; //NOSONAR

}
