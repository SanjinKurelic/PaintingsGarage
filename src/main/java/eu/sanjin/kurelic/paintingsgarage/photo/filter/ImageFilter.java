package eu.sanjin.kurelic.paintingsgarage.photo.filter;

import java.awt.image.BufferedImage;

public interface ImageFilter {

  BufferedImage apply(BufferedImage image);
}
