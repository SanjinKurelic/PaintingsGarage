package eu.sanjin.kurelic.photostorage.modules.user.mapper;

import eu.sanjin.kurelic.photostorage.common.model.SearchResult;
import eu.sanjin.kurelic.photostorage.modules.user.entity.User;
import eu.sanjin.kurelic.photostorage.modules.user.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface UserMapper {

  Author mapUserToAuthor(User user);

  @Mapping(target = "value", source = "name")
  SearchResult mapUserToSearchResult(User user);

  List<SearchResult> mapUserListToSearchResultList(List<User> userList);
}
