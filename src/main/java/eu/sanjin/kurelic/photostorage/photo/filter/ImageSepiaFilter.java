package eu.sanjin.kurelic.photostorage.photo.filter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageSepiaFilter implements ImageFilter {

  private static final int SEPIA_DEPTH = 20;
  private static final int SEPIA_INTENSITY = 10;

  @Override
  public BufferedImage apply(BufferedImage image) {
    var result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

    int w = image.getWidth();
    int h = image.getHeight();

    // We need 3 integers (for R,G,B color values) per pixel.
    var pixels = new int[w * h * 3];
    image.getRaster().getPixels(0, 0, w, h, pixels);

    for (var x = 0; x < image.getWidth(); x++) {
      for (var y = 0; y < image.getHeight(); y++) {
        var rgb = image.getRGB(x, y);
        var color = new Color(rgb, true);
        var r = color.getRed();
        var g = color.getGreen();
        var b = color.getBlue();
        int gry = (r + g + b) / 3;

        r = g = b = gry;
        r = r + (SEPIA_DEPTH * 2);
        g = g + SEPIA_DEPTH;

        if (r > 255) {
          r = 255;
        }
        if (g > 255) {
          g = 255;
        }
        if (b > 255) {
          b = 255;
        }

        // Darken blue color to increase sepia effect
        b -= SEPIA_INTENSITY;
        if (b < 0) {
          b = 0;
        }

        color = new Color(r, g, b, color.getAlpha());
        result.setRGB(x, y, color.getRGB());
      }
    }

    return result;
  }
}
