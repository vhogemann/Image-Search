package com.hogemann.image.service

import javax.annotation.Resource
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces

import org.jboss.resteasy.plugins.providers.multipart.InputPart
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput
import org.springframework.beans.factory.annotation.Autowired

import com.hogemann.image.index.Indexer
import com.hogemann.image.index.Searcher

@Resource
@Path('/search')
class Search {

	Searcher<String[]> searcher
	
	@POST
	@Path("/upload")
	@Consumes("multipart/form-data")
	@Produces("application/json")
	List<String> search( MultipartFormDataInput input ){
		
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap()
		List<InputPart> parts = uploadForm.get('image')
		
		def result = []
		
		parts.each { part ->
			InputStream image = part.getBody(InputStream.class, null)
			result.addAll( searcher.search(image) )
		}
		
		return result
	}
}
