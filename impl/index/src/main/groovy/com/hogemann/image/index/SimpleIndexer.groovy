package com.hogemann.image.index

import java.io.InputStream;

import net.semanticmetadata.lire.DocumentBuilderFactory

import org.apache.lucene.analysis.SimpleAnalyzer
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.store.FSDirectory
import org.apache.lucene.util.Version

import com.hogemann.image.index.util.ImageUtil

class SimpleIndexer implements Indexer {

	def writer
	
	def builder = DocumentBuilderFactory.getFullDocumentBuilder()
	
	public SimpleIndexer(def path){
		 def index = new File(path)
		 def conf = new IndexWriterConfig(Version.LUCENE_33, new SimpleAnalyzer())
		 writer = new IndexWriter(FSDirectory.open(index), conf)
	}
	
	public void index(InputStream stream, String identifier) {
		def image = ImageUtil.crop(stream)
		def doc = builder.createDocument( image , identifier )
		writer.addDocument(doc)
	}
	
	public void close(){
		writer.optimize()
		writer.close()
	}

}
