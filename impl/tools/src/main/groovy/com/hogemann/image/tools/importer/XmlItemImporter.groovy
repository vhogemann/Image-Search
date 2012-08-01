package com.hogemann.image.tools.importer

class XmlItemImporter {
	
	def indexer
	
	def execute(def path){
		
		new File(path).eachFileRecurse { file ->
			println file.name
			if( ! file.isDirectory() ){
				def item = new XmlSlurper().parse(file).declareNamespace(item:'http://www.b2winc.com/item')
				
				def level1 = item
					.'item:siteDataInfo'
						.'item:siteInfo'
							.'item:siteMerchandiseHierarchy'
								.'item:main'
									.'item:level1'
										.'item:id'
				
										
				if ( "$level1" == "256705" ){
					item.'item:itemProductionData'.'item:imageList'.'item:imageInfo'.each { info ->
						def url ="${info.'item:fileName'}" 
						println url
						try{
							def image = new URL("$url").openStream()
							indexer.index(image,"$url")
						} catch(Exception e){
							e.printStackTrace()
						}
					}
				}
			}
		}
	}
	
}
