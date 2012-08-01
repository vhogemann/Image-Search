package com.hogemann.image.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import android.os.AsyncTask;
import android.util.Log;

import com.hogemann.image.TaskDelegate;

public class SearchTask extends AsyncTask<String, Void, List<String>> {

	private TaskDelegate<List<String>> delegate;
	
	private Throwable error;

	private String url = "http://192.168.2.149:8080/search/upload";
	
	public SearchTask(TaskDelegate<List<String>> delegate) {
		this.delegate = delegate;
	}
	
	@Override
	protected List<String> doInBackground(String... params) {
		if(params != null){
			try {
				File file = new File(params[0]);
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(url );
				MultipartEntity multipart = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
				multipart.addPart("image", new FileBody(file));
				request.setEntity(multipart);
				
				Log.i("Image Search","Starting search");
				
				HttpResponse response = client.execute(request);
				int status = response.getStatusLine().getStatusCode();
				if(status == 200){
					String json = EntityUtils.toString(response.getEntity());
					JSONArray array = new JSONArray(json);
					List<String> result = new ArrayList<String>();
					for(int x = 0; x < array.length(); x++){
						String url = array.getString(x);
						Log.d("Image Search", "image: " + url);
						result.add(url);
					}
					return result;
				} else {
					error = new RuntimeException(response.getStatusLine().getReasonPhrase());
				}
				
			} catch (Exception e) {
				error = e;
				Log.e("Image Search", "erro buscando", e);
			}
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(List<String> result) {
		if(result != null){
			delegate.onSuccess(result);
		} else if (error != null){
			delegate.onFailure(error.getMessage());
		} else {
			delegate.onFailure("Erro desconhecido");
		}
	}

}
