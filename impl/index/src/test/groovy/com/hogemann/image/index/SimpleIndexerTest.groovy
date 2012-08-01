package com.hogemann.image.index

import org.junit.Test;

import com.hogemann.image.index.SimpleIndexer;

class SimpleIndexerTest {

	@Test
	public void indexTest(){
	
		def indexer = new SimpleIndexer(System.getProperty('java.io.tmpdir') + '/image_idx')
		
		def images = new File('src/test/resources/images')
		images.eachFile { image ->
			indexer.index(new FileInputStream( image ), image.name)
		}
	}
	
}
