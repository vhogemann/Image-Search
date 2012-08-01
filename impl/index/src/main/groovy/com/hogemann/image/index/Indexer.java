package com.hogemann.image.index;

import java.io.InputStream;

public interface Indexer {
	
	public void index( InputStream image, String identifier );
	
	public void close();

}
