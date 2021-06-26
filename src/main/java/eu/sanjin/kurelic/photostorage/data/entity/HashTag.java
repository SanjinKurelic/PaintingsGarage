package eu.sanjin.kurelic.photostorage.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "hashtag")
public class HashTag implements Serializable {

  @Id
  private Long id;

  @ManyToOne
  @JoinColumn(name = "photo_id", referencedColumnName = "id")
  private Photo photo;

  private String name;
}
