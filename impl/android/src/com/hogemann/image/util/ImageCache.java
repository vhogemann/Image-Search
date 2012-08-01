package com.hogemann.image.util;

import java.util.Map;
import java.util.WeakHashMap;

import android.graphics.Bitmap;

//TODO FILE CACHE

public class ImageCache {
	
	final static int WIDTH = 90;
	final static int HEIGHT = 90;
	
	private Map<String, Bitmap> cache = new WeakHashMap<String, Bitmap>();
	
	private static ImageCache instance;
	
	private ImageCache() {/*Singleton Constructor*/}
	
	public static synchronized ImageCache getInstance(){
		if(instance == null)
			instance = new ImageCache();
		return instance;
	}
	
	public Bitmap get(String key){
		return cache.get(key);
	}
	
	public synchronized void put(String key, Bitmap value){
		cache.put(key, downsample(value));
	}
	
	private synchronized Bitmap downsample(Bitmap original){
        Bitmap bitmap = Bitmap.createScaledBitmap(original, WIDTH, HEIGHT, true);
        return bitmap;
	}

}
