package eu.sanjin.kurelic.photostorage.mapper;

import eu.sanjin.kurelic.photostorage.data.entity.User;
import eu.sanjin.kurelic.photostorage.model.SearchModel;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface UserMapper {

  @Named("mapUserToSearchModel")
  @Mapping(target = "value", source = "username")
  @Mapping(target = "type", constant = "AUTHOR")
  SearchModel mapUserToSearchModel(User user);

  @IterableMapping(qualifiedByName = "mapUserToSearchModel")
  List<SearchModel> mapUserListToSearchModelList(List<User> userList);

}
