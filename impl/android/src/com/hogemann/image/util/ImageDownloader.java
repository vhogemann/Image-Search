package com.hogemann.image.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.BlockingQueue;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

public class ImageDownloader implements Runnable {

	private BlockingQueue<ImageDownloadRequest> queue;
	private ImageCache cache;
	
	public ImageDownloader(BlockingQueue<ImageDownloadRequest> queue) {
		this.queue = queue;
		this.cache = ImageCache.getInstance();
	}
	
	public void run() {
		while(true){
			try {
				ImageDownloadRequest request = queue.take();
				ImageView view = request.getView();
				String spec = request.getUrl();
				Log.d("Image Download","downloading: " + spec);
				if(spec != null && cache.get(spec) == null){
					try {
						URL url = new URL(spec);
						URLConnection connection = url.openConnection();
						connection.setUseCaches(true);
						final Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
						if(bitmap != null){
							Log.d("Image Download","success downloading image: " + spec);
							cache.put(spec, bitmap);
							updateBitmap(request.getId(),view, cache.get(spec));
						}
					} catch (MalformedURLException e) {
						Log.e("Image Download", "malformed URL " + spec);
					} catch (IOException e) {
						Log.e("Image Download", "error transfering image", e);
					}
					
				} else if( spec != null && cache.get(spec) != null){
					Bitmap bitmap = cache.get(spec);
					updateBitmap(request.getId(), view, bitmap);
				}
				
			} catch (InterruptedException e) {
				Log.w("Image Download", "thread interrupted");
			}
		}

	}
	
	private void updateBitmap(String id, final ImageView view, final Bitmap bitmap){
		Object obj = view.getTag();
		if(obj != null){
			String tag = null;
			if(obj instanceof String){
				tag = obj.toString();
			}
			if(id.equals(tag)){
				view.post(new Runnable() {
					public void run() {
						view.setImageBitmap(bitmap);									
					}
				});
			}
		}
	}

}
