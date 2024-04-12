package com.movie.FilmMatch.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("Cradite")
public class CraditeVo {

	String gender;
    String name;
    String profile_path;
    String character;
    String known_for_department;

	public CraditeVo(String gender, String name, String profile_path, String character, String known_for_department) {
		this.gender = gender;
		this.name = name;
		this.profile_path = profile_path;
		this.character = character;
		this.known_for_department = known_for_department;
	}
		
}
