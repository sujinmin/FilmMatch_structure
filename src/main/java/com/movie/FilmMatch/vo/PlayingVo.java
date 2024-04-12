package com.movie.FilmMatch.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("Playing")
public class PlayingVo {

	String id;
	String title;
	String overview;
	String poster_path;
	String release_date;
	String popularity;
	String vote_count;
	String genre_ids;

	List<Integer> genre_list;
	List<CraditeVo> cradite_list;

	
	public PlayingVo(String id, String title, String overview, String poster_path, String release_date,
			String popularity, String vote_count, String genre_ids, List<Integer> genre_list,
			List<CraditeVo> cradite_list) {
		this.id = id;
		this.title = title;
		this.overview = overview;
		this.poster_path = poster_path;
		this.release_date = release_date;
		this.popularity = popularity;
		this.vote_count = vote_count;
		this.genre_ids = genre_ids;
		this.genre_list = genre_list;
		this.cradite_list = cradite_list;
	}



	
	
}
