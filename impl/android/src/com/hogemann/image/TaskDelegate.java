package com.hogemann.image;

public interface TaskDelegate<T> {

	public void onSuccess(T result);
	
	public void onFailure(String message);
	
}
