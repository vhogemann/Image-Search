package com.hogemann.image.index.util

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

static InputStream crop (InputStream stream){
	BufferedImage img = ImageIO.read(stream);
	ByteArrayOutputStream os = new ByteArrayOutputStream();
	ImageIO.write(crop(img),"jpg",os)
	return new ByteArrayInputStream(os.toByteArray());
}

static BufferedImage crop( BufferedImage img ){
	
	int width = img.getWidth()
	int height = img.getHeight()
	
	int mid_width = Math.floor( width / 2 )
	int mid_heigh = Math.floor( height / 2 ) 
	
	def top = 0
	for(int y = 0; y < height; y++ ){
		top = y
		def color = new Color(img.getRGB(mid_width, y))
		if(isWhite(color)) break
	}
	
	def bottom = height
	for(int y = ( height - 1 ); y > 0; y--){
		bottom = y
		def color = new Color(img.getRGB(mid_width, y))
		if(isWhite(color)) break
	}
	
	def left = 0
	for(int x = 0; x < width; x++){
		left = x
		def color = new Color(img.getRGB(x, mid_heigh))
		if(isWhite(color)) break
	}
	
	def right = width
	for(int x = ( width - 1 ); x > 0; x--){
		right = x
		def color = new Color(img.getRGB(x, mid_heigh))
		if(isWhite(color)) break
	}
	
	def crop_width = right - left
	def crop_height = bottom - top
	
	return img.getSubimage(left, top, crop_width, crop_height)
}

static boolean isWhite(Color color){
	return ! ( color.getRed() > 240 && color.getGreen() > 240 && color.getBlue() > 240 );	
}

