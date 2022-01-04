package eu.sanjin.kurelic.photostorage.photo.entity;

import eu.sanjin.kurelic.photostorage.hashtag.entity.Hashtag;
import eu.sanjin.kurelic.photostorage.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

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

  private String title;

  private String description;

  private Integer size;

  private LocalDateTime uploaded;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User author;

  // We always require hashtags with photo entity
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "hashtag_photo",
    joinColumns = @JoinColumn(name = "photo_id"),
    inverseJoinColumns = @JoinColumn(name = "hashtag_id")
  )
  private Set<Hashtag> hashtags;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "photo_owner",
    joinColumns = {@JoinColumn(name = "photo_id")},
    inverseJoinColumns = {@JoinColumn(name = "user_id")}
  )
  private Set<User> owners;
}
