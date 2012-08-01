package com.hogemann.image;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Gallery;

import com.hogemann.image.adapter.GalleryAdapter;
import com.hogemann.image.task.SearchTask;
import com.hogemann.image.util.ImageDownloadManager;
import com.ideais.image.R;

public class SearchActivity extends Activity {
	
	private static final String IMAGE_SEARCH_JPG = "image_search.jpg";

	static final int SHOOT_PICTURE = 0;
	
	private Button search;
	private Gallery gallery;
	private GalleryAdapter adapter;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ImageDownloadManager.getInstance();
        
        adapter = new GalleryAdapter(this);
        gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setAdapter(adapter);
        
        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				File photo = new File(Environment.getExternalStorageDirectory(), IMAGE_SEARCH_JPG);
				intent.putExtra(MediaStore.EXTRA_OUTPUT , Uri.fromFile(photo));
				startActivityForResult(intent, SHOOT_PICTURE);
			}
		});
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if( requestCode == SHOOT_PICTURE){
    		if( resultCode == RESULT_OK ){
    			Log.d("Image Search", "... starting search");
	    		SearchTask task = new SearchTask(adapter);
	    		String path = Environment.getExternalStorageDirectory()+ File.pathSeparator + IMAGE_SEARCH_JPG;
	    		Uri uri = Uri.fromFile(new File(path));
	    		if( data!= null )
	    			uri = Uri.parse(data.toURI());
    			path = uri.getPath();
	    		task.execute(uri.getPath());
    		} else {
    			Log.e("Image Search","error feching image!");
    		}
    	}
    }
}