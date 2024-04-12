package com.movie.FilmMatch.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("actorprof")
public class ActorprofVo {


	String biography;
	String birthday;
	String homepage;


	public ActorprofVo(String biography, String birthday, String homepage) {
		this.biography = biography;
		this.birthday = birthday;
		this.homepage = homepage;
	}

	
}
