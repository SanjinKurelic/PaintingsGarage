package eu.sanjin.kurelic.paintingsgarage.photo.filter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageInvertFilter implements ImageFilter {

  @Override
  public BufferedImage apply(BufferedImage image) {
    var result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

    for (var x = 0; x < image.getWidth(); x++) {
      for (var y = 0; y < image.getHeight(); y++) {
        var color = new Color(image.getRGB(x, y), true);
        var r = 255 - color.getRed();
        var g = 255 - color.getGreen();
        var b = 255 - color.getBlue();

        color = new Color(r, g, b, color.getAlpha());
        result.setRGB(x, y, color.getRGB());
      }
    }

    return result;
  }
}
