package com.hogemann.image.tools.importer

import org.junit.Test

import com.hogemann.image.tools.importer.XmlItemImporter
import com.hogemann.image.index.SimpleIndexer

class XmlItemImporterTest {
	
	def index_path = '/home/vhogemann/Projects/Ideais/imagesearch/image_idx'

	@Test
	public void executeTest(){
		def importer = new XmlItemImporter()
		importer.indexer = new SimpleIndexer(index_path)
		(0..9).each { x ->
			(0..9).each { y ->
				println "$x $y"
				importer.execute("/home/vhogemann/Projects/B2W/item_xml/indexed/$x/$y")
			}
			importer.indexer.close()
			importer.indexer = new SimpleIndexer(index_path)
		}
	}
	
}
