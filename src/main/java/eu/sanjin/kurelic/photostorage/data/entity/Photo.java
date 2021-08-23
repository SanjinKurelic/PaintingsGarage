package eu.sanjin.kurelic.photostorage.data.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "photo")
public class Photo implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
