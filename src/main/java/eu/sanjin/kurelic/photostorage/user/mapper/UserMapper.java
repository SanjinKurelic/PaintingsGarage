package eu.sanjin.kurelic.photostorage.user.mapper;

import eu.sanjin.kurelic.photostorage.search.model.SearchResult;
import eu.sanjin.kurelic.photostorage.user.entity.User;
import eu.sanjin.kurelic.photostorage.user.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface UserMapper {

  Author mapUserToAuthor(User user);

  List<Author> mapUserListToAuthorList(List<User> user);

  @Mapping(target = "value", source = "name")
  SearchResult mapUserToSearchResult(User user);

  List<SearchResult> mapUserListToSearchResultList(List<User> userList);
}
