package eu.sanjin.kurelic.photostorage.service;

import eu.sanjin.kurelic.photostorage.data.repository.HashTagRepository;
import eu.sanjin.kurelic.photostorage.data.repository.PhotoRepository;
import eu.sanjin.kurelic.photostorage.data.repository.UserRepository;
import eu.sanjin.kurelic.photostorage.exceptions.ErrorCode;
import eu.sanjin.kurelic.photostorage.exceptions.WrongArgumentException;
import eu.sanjin.kurelic.photostorage.mapper.HashTagMapper;
import eu.sanjin.kurelic.photostorage.mapper.PhotoMapper;
import eu.sanjin.kurelic.photostorage.mapper.UserMapper;
import eu.sanjin.kurelic.photostorage.model.PhotoData;
import eu.sanjin.kurelic.photostorage.model.SearchModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

  private final HashTagRepository hashTagRepository;
  private final HashTagMapper hashTagMapper;
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PhotoRepository photoRepository;
  private final PhotoMapper photoMapper;

  public List<PhotoData> findImages(List<SearchModel> searchModels) {
    var authorIds = new ArrayList<Long>();
    var hashTagIds = new ArrayList<Long>();
    Integer size = null;
    LocalDateTime from = null;
    LocalDateTime to = null;

    for (var searchModel : searchModels) {
      switch (searchModel.getType()) {
        case AUTHOR:
          authorIds.add(searchModel.getId());
          break;
        case DATE:
          var fromTo = searchModel.getValue().split("-");
          if (fromTo.length != 2) {
            throw new WrongArgumentException(ErrorCode.WRONG_DATE_FROM_TO_ARGUMENT);
          }
          from = parseDate(fromTo[0]);
          to = parseDate(fromTo[1]);
          break;
        case HASHTAG:
          hashTagIds.add(searchModel.getId());
          break;
        case SIZE:
          try {
            size = Integer.valueOf(searchModel.getValue());
          } catch (NumberFormatException ignore) {
            throw new WrongArgumentException(ErrorCode.WRONG_SIZE_ARGUMENT);
          }
          break;
      }
    }

    return photoMapper.mapPhotoListToPhotoDataList(photoRepository.findAllBy(authorIds, hashTagIds, size, from, to));
  }

  public List<SearchModel> findHashTag(String name) {
    if (name.length() < 2) {
      return List.of();
    }

    return hashTagMapper.mapHashTagListToSearchModelList(hashTagRepository.findAllByNameLike(name));
  }

  public List<SearchModel> findAuthor(String authorName) {
    if (authorName.length() < 2) {
      return List.of();
    }

    return userMapper.mapUserListToSearchModelList(userRepository.findAllByUsernameLike(authorName));
  }

  private LocalDateTime parseDate(String value) {
    try {
      return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    } catch (DateTimeParseException ignore) {
      throw new WrongArgumentException(ErrorCode.WRONG_DATE_FROM_TO_ARGUMENT);
    }
  }

}