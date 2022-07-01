package eu.sanjin.kurelic.paintingsgarage.metric.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum TimerMetricName {

  IMAGE_GREYSCALE_FILTER_SPEED("Measure time needed for greyscale image filter"),
  IMAGE_INVERT_FILTER_SPEED("Measure time needed for invert image filter"),
  IMAGE_SEPIA_FILTER_SPEED("Measure time needed for sepia image filter");

  private final String description;
}
