package com.hogemann.image.index

class ImageHit implements Comparable<ImageHit> {
	
	def score = 0
	
	def ocurrences = 0
	
	def url

	public int compareTo(ImageHit ih) {
		
		if( ih.ocurrences != ocurrences )
			return ocurrences - ih.ocurrences
		
		return score - ih.score;
	}
	
	@Override
	public boolean equals(Object obj) {
		if( obj instanceof ImageHit ){
			def ih = (ImageHit) obj
			if( url.equals( ih.url ) ){
				ocurrences++
				return true
			}
		}
		
		return false
	}
	
	@Override
	public int hashCode() {
		return url.hashCode();
	}
}
