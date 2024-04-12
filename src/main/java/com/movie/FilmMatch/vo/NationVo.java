package com.movie.FilmMatch.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("nation")
public class NationVo {

	String id;
	String original_language;
	String overview;
	String title;
	String poster_path;

	
	public NationVo(String id, String original_language, String overview, String title, String poster_path) {
		this.id = id;
		this.original_language = original_language;
		this.overview = overview;
		this.title = title;
		this.poster_path = poster_path;
	}



	
	
	
}
