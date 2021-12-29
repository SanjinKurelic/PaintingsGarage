package eu.sanjin.kurelic.photostorage.modules.hashtag.entity;

import eu.sanjin.kurelic.photostorage.modules.photo.entity.Photo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hashtag")
public class Hashtag implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToMany
  @JoinTable(
    name = "hashtag_photo",
    joinColumns = @JoinColumn(name = "hashtag_id"),
    inverseJoinColumns = @JoinColumn(name = "photo_id")
  )
  private List<Photo> photoList;
}
