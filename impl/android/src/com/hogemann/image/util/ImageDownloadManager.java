package com.hogemann.image.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import android.util.Log;

public class ImageDownloadManager {
	
	static final int THREADNUM = 3;
	
	static private ImageDownloadManager instance;
	static private BlockingQueue<ImageDownloadRequest> queue;
	
	private ImageDownloadManager(){ /*Singleton*/ }
	
	static public ImageDownloadManager getInstance(){
		
		getQueue();
		
		if(instance == null){
			instance = new ImageDownloadManager();
			instance.startThreads();
		}
		return instance;
	}

	private void startThreads() {
		for(int i = 0; i < THREADNUM; i++){
			Log.i("Image Download Manager", "Starting thread " + i);
			new Thread(new ImageDownloader(getQueue())).start();
		}
	}

	static public BlockingQueue<ImageDownloadRequest> getQueue() {
		if(queue == null){
			queue = new PriorityBlockingQueue <ImageDownloadRequest>();
		}
		return queue;
	}

}
