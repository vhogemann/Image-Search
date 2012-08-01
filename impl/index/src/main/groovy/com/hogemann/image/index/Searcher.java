package com.hogemann.image.index;

import java.io.InputStream;

public interface Searcher<R> {
	
	public R search(InputStream image);
	
}
