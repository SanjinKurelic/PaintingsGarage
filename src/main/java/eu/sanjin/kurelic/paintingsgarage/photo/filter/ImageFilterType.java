package eu.sanjin.kurelic.paintingsgarage.photo.filter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.awt.image.BufferedImage;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ImageFilterType {
  GREYSCALE(new ImageGreyscaleFilter()),
  INVERT(new ImageInvertFilter()),
  SEPIA(new ImageSepiaFilter());

  private final ImageFilter filter;

  public ImageFilter getFilter() {
    return filter;
  }

  public BufferedImage applyFilter(BufferedImage image) {
    return this.filter.apply(image);
  }
}
