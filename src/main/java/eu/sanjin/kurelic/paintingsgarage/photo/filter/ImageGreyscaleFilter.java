package eu.sanjin.kurelic.paintingsgarage.photo.filter;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

public class ImageGreyscaleFilter implements ImageFilter {

  @Override
  public BufferedImage apply(BufferedImage image) {
    var result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

    var op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
    op.filter(image, result);

    return result;
  }
}
