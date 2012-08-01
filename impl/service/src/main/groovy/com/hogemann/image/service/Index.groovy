package com.hogemann.image.service

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.hogemann.image.index.Indexer;

@Path("/index")
class Index {
	
	Indexer indexer

	@POST
	@Path("/")
	public String index(String url, String description){
		def image = new URL(url).openStream()
		indexer.index(image, description)
		return "success"
	}
	
}
