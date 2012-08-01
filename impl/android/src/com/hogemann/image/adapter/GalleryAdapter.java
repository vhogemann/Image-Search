package com.hogemann.image.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.hogemann.image.TaskDelegate;
import com.hogemann.image.util.ImageCache;
import com.hogemann.image.util.ImageDownloadManager;
import com.hogemann.image.util.ImageDownloadRequest;
import com.ideais.image.R;

public class GalleryAdapter extends BaseAdapter implements TaskDelegate<List<String>>{

	private List<String> images;
	
	private Activity activity;
	
	public GalleryAdapter(Activity activity) {
		this.activity = activity;
	}
	
	@Override
	public int getCount() {
		if(images == null)
			return 0;
		return images.size();
	}

	@Override
	public Object getItem(int position) {
		return images.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = null;
		if( convertView == null ){
			imageView = new ImageView(activity);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setLayoutParams(new Gallery.LayoutParams(200,200));
		} else {
			imageView = (ImageView) convertView;
		}
		
		Bitmap image = ImageCache.getInstance().get(images.get(position));
		if(image == null)
			imageView.setImageResource(R.drawable.img_placeholder);
		else
			imageView.setImageBitmap(image);

		imageView.setTag(images.get(position));
		ImageDownloadManager.getQueue().add(new ImageDownloadRequest(images.get(position), images.get(position), imageView));
		
		return imageView;
	}
	
	public void onFailure(String reason){
		Toast.makeText(activity, reason, Toast.LENGTH_SHORT);
		images = null;
		notifyDataSetChanged();
	}
	
	public void onSuccess(List<String> result){
		Toast.makeText(activity, "Busca finalizada", Toast.LENGTH_SHORT);
		images = result;
		notifyDataSetChanged();
	}

}
