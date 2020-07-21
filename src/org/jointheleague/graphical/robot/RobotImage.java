package org.jointheleague.graphical.robot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * Utility class for loading images in robi format.
 * 
 * @author David Dunn &amp; Erik Colban &copy; 2016
 *
 */
public class RobotImage {
	private static final int IMG_WIDTH = 100;
	private static final int IMG_HEIGHT = 100;
	private static final int PIXEL_LENGTH = 20; // in bytes

	public static BufferedImage loadDefaultRobi() {
		return loadRobi("rob");
	}

	public static BufferedImage loadRobi(String s) {
		s = String.format("res/%s.robi", s);
		BufferedImage img = new BufferedImage(IMG_WIDTH, IMG_HEIGHT,
				BufferedImage.TYPE_INT_ARGB);

		try (InputStream is = RobotImage.class.getResourceAsStream(s)) {
			if (is == null) {
				throw new IOException("File not found: " + s);
			}
			byte[] buf = new byte[PIXEL_LENGTH * 256];
			IntBuffer ibuf = ByteBuffer.wrap(buf).asIntBuffer();
			int offset = 0;
			int len;
			while ((len = is.read(buf, offset, buf.length - offset)) != -1) {
				len += offset;
				offset = len % PIXEL_LENGTH;
				len -= offset;
				for (int i = 0; i < len; i += PIXEL_LENGTH) {
					readPixel(img, ibuf, i / 4);
				}
				for (int i = 0; i < offset; i++) {
					buf[i] = buf[len + i];
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"There was an error loading your file.");
			System.out.println("RobotImage: loadRobi\n" + e.getMessage());
			img = loadDefaultRobi();
		}
		return img;
	}

	private static void readPixel(BufferedImage img, IntBuffer ibuf, int pos) {
		int x = ibuf.get(pos++);
		int y = ibuf.get(pos++);

		// Next 3 ints are the r, g, b components of the color
		int rgb = 0;
		for (int i = 0; i < 3; i++) {
			rgb = rgb << 8 | ibuf.get(pos++) & 0xff;
		}
		// The color 0xdcdcdc is chosen to encode transparency
		if (rgb != 0xdcdcdc) {
			rgb |= 0xff000000;
		}

		img.setRGB(x, y, rgb);
	}

	public static BufferedImage loadImage(File file) {
		try {
			BufferedImage image = ImageIO.read(file);
			if (image != null) {
				return image;
			}
		} catch (IOException e) 
		{
			System.out.println(e.getMessage());
			return loadDefaultRobi();
		}
		return loadDefaultRobi();
	}

}
