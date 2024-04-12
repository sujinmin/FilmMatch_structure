package com.movie.FilmMatch.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("knownfor")
public class KnownforVo {

	String id;
	String title;
	String overview;
	String poster_path;
	String media_type;
	String vote_average;
	String vote_count;
	String origin_country;


	public KnownforVo(String id, String title, String overview, String poster_path, String media_type,
			String vote_average, String vote_count, String origin_country) {
		this.id = id;
		this.title = title;
		this.overview = overview;
		this.poster_path = poster_path;
		this.media_type = media_type;
		this.vote_average = vote_average;
		this.vote_count = vote_count;
		this.origin_country = origin_country;
		
	}
	

	

	
}
