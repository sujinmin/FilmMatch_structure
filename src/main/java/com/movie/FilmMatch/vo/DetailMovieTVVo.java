package com.movie.FilmMatch.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("detailmovietv")
public class DetailMovieTVVo{


	String budget;
	String original_language;
	String original_title;
	String overview;
	String popularity;
	String poster_path;
	String release_date;
	String revenue;
	String runtime;
	

	List<String> genres_list;
	List<CompanyVo> companies_list;
	List<String> countries_list;

	public DetailMovieTVVo() {

	}

	public DetailMovieTVVo(String budget, String original_language, String original_title,
			String overview, String popularity, String poster_path, 
			String release_date, String revenue, String runtime, 
			List<String> genres_list, List<CompanyVo> companies_list, List<String> countries_list) {
		this.budget = budget;

		this.original_language = original_language;
		this.original_title = original_title;
		this.overview = overview;
		this.popularity = popularity;
		this.poster_path = poster_path;
		this.release_date = release_date;
		this.revenue = revenue;
		this.runtime = runtime;
		
		this.genres_list = genres_list;
		this.companies_list = companies_list;
		this.countries_list = countries_list;
	}

	
}
