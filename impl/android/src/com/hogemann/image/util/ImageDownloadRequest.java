package com.hogemann.image.util;

import android.widget.ImageView;

public class ImageDownloadRequest implements Comparable<ImageDownloadRequest>{
	
	protected long creation;
	private String id;
	private String url;
	private ImageView view;
	
	public ImageDownloadRequest(String id, String url, ImageView view) {
		
		if(id == null)
			throw new IllegalArgumentException("id must not be null");
		
		this.id = id;
		this.url = url;
		this.view = view;
		
		creation = System.currentTimeMillis();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ImageDownloadRequest){
			ImageDownloadRequest another = (ImageDownloadRequest)obj;
			return id.equals(another.id);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	public String getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public ImageView getView() {
		return view;
	}

	public int compareTo(ImageDownloadRequest another) {
		return Math.round(another.creation - creation);
	}
}
