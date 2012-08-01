package com.hogemann.image.index

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.semanticmetadata.lire.DocumentBuilder;
import net.semanticmetadata.lire.ImageSearcher;
import net.semanticmetadata.lire.ImageSearcherFactory;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

class SimpleSearcher implements Searcher<String[]> {

	def path
	
	public String[] search(InputStream stream) {
		IndexReader reader = IndexReader.open( FSDirectory.open(new File(path)) )
		
		def searchers = [
			//ImageSearcherFactory.createSimpleSearcher(5),
			//ImageSearcherFactory.createAutoColorCorrelogramImageSearcher(5),
			//ImageSearcherFactory.createColorHistogramImageSearcher(5),
			ImageSearcherFactory.createColorLayoutImageSearcher(5),
			//ImageSearcherFactory.createDefaultSearcher(),
			ImageSearcherFactory.createEdgeHistogramImageSearcher(5), //Bom
			//ImageSearcherFactory.createGaborImageSearcher(5),
			ImageSearcherFactory.createJCDImageSearcher(5), //Bom
			ImageSearcherFactory.createJpegCoefficientHistogramImageSearcher(5), //Bom
			//ImageSearcherFactory.createScalableColorImageSearcher(5),
			//ImageSearcherFactory.createTamuraImageSearcher(5)
		]
		
		BufferedImage image = ImageIO.read(stream)
		
		def image_set = [] as Set
		
		searchers.each { searcher ->
			def hits = searcher.search(image, reader);
			if (hits) {
				for( int i = 0; i < hits.length(); i++ ){
					def doc = hits.doc(i)
					def hit = new ImageHit();
					hit.url = doc.get(DocumentBuilder.FIELD_NAME_IDENTIFIER)
					hit.score = hits.score(i);
					image_set.add(hit)
				}
			}
		}
		
		println image_set.size()
		
		return image_set.sort().collect(){ hit -> hit.url };
	}
	
}
