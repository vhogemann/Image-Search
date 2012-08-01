package com.hogemann.image.tools

import com.hogemann.image.tools.importer.XmlItemImporter
import com.hogemann.image.index.SimpleIndexer

class App {
	public static void main(String[] args) {
		
		def cli = new CliBuilder(usage: 'tools')
		cli.path(args: 1, argName: 'path', 'path to the xml files to be indexed (recursive)')
		cli.index(args: 1, argName: 'path', 'where to index the images')
		def opts = cli.parse(args)
		
		def path = opts.path ?: false
		def index = opts.index ?: false
		
		if(path && index){
			def importer = new XmlItemImporter()
			importer.indexer = new SimpleIndexer(index)
			importer.execute(path)
			importer.indexer.close()
		} else {
			cli.usage()
		}
		
	}
}
