package com.movie.FilmMatch.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("Poster")
public class PosterVo {

	String id;
	String title;
	String overview;
	String popularity;
	String poster_path;
	String release_date;
	String vote_average;

	
	public PosterVo(String id,String title, String overview, String popularity, String poster_path, String release_date,String vote_average) {
		this.id = id;
		this.title = title;
		this.overview = overview;
		this.popularity = popularity;
		this.poster_path = poster_path;
		this.release_date = release_date;
		this.vote_average = vote_average;
	}

	
	
	
}
