package eu.sanjin.kurelic.paintingsgarage.metric.mapper;

import eu.sanjin.kurelic.paintingsgarage.metric.model.TimerMetricName;
import eu.sanjin.kurelic.paintingsgarage.photo.filter.ImageFilterType;
import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;

@Mapper
public interface ImageFilterMapper {

  @ValueMapping(target = "IMAGE_GREYSCALE_FILTER_SPEED", source = "GREYSCALE")
  @ValueMapping(target = "IMAGE_INVERT_FILTER_SPEED", source = "INVERT")
  @ValueMapping(target = "IMAGE_SEPIA_FILTER_SPEED", source = "SEPIA")
  TimerMetricName mapImageFilterTypeToTimerMetricName(ImageFilterType imageFilterType);
}
