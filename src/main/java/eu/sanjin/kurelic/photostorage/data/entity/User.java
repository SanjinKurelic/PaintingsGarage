package eu.sanjin.kurelic.photostorage.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Serializable {

  @Id
  private Long id;

  private String username;
}
