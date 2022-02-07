package eu.sanjin.kurelic.photostorage.photo.filter;

import java.awt.image.BufferedImage;

public interface ImageFilter {

  BufferedImage apply(BufferedImage image);
}
