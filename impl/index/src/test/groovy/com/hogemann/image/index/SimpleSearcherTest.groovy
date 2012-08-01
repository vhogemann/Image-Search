package com.hogemann.image.index

import org.junit.Test;

import com.hogemann.image.index.SimpleSearcher;

class SimpleSearcherTest {
	
	@Test
	public void searchTest(){
		def searcher = new SimpleSearcher()
		searcher.path = '/home/vhogemann/Projects/Ideais/imagesearch/image_idx'
		
		//def file = 'src/test/resources/images/aesperanca.jpg'
		//def file = 'src/test/resources/images/terapiadosanjos.jpg'
		//def file = 'src/test/resources/photos/oespiao.jpg'
		def file = 'src/test/resources/images/viveravida.jpg'
		//def file = 'src/test/resources/images/memoriasdeumagueixa.jpg'
		//def file = 'src/test/resources/photos/spookycountry.jpg'
		
		def image = new File(file)
		
		def result = searcher.search(new FileInputStream(image));
		
		result.each { line ->
			println line
		}
		
	}

}
