package eu.sanjin.kurelic.photostorage.data.entity;

import lombok.Getter;
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
@Table(name = "hashtag")
public class HashTag implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @ManyToMany
  @JoinTable(
    name = "hashtag_photo",
    joinColumns = @JoinColumn(name = "hashtag_id"),
    inverseJoinColumns = @JoinColumn(name = "photo_id")
  )
  private List<Photo> photoList;

  private String name;
}
