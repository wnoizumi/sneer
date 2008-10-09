package sneer.skin.image;

import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;

import wheel.lang.exceptions.Hiccup;

public interface ImageFactory extends DefaultIcons{

	ImageIcon getIcon(File file);
	ImageIcon getIcon(String relativeImagePath);
	ImageIcon getIcon(Class<?> anchor,	String relativeImagePath);

	BufferedImage createBufferedImage(Image image) throws Hiccup;
	BufferedImage toCompatibleImage(BufferedImage image, GraphicsConfiguration gc);
	BufferedImage copy(BufferedImage source, BufferedImage target);

	GraphicsConfiguration getDefaultConfiguration();

	BufferedImage getScaledInstance(Image image, double scale) throws Hiccup;
	BufferedImage getScaledInstance(Image image, int width, int height) throws Hiccup;
	BufferedImage getScaledInstance(Image image, int width, int height, GraphicsConfiguration gc) throws Hiccup;
	
	Image toImage(int width, int height, int[] data);
	int[] toSerializableData(BufferedImage img) throws Hiccup;

}