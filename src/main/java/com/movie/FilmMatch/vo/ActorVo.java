package com.movie.FilmMatch.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("actor")
public class ActorVo {


	String id;
	String gender;
	String name;
	String profile_path;
	

	List<KnownforVo> known_forlist;
	List<ActorprofVo> actorprof_list;
	public ActorVo(String id, String gender, String name, String profile_path, List<KnownforVo> known_forlist,
			List<ActorprofVo> actorprof_list) {
		this.id = id;
		this.gender = gender;
		this.name = name;
		this.profile_path = profile_path;
		this.known_forlist = known_forlist;
		this.actorprof_list = actorprof_list;
	}

	


	
}
