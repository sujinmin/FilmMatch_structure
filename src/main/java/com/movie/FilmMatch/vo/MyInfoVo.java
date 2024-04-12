package com.movie.FilmMatch.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;
@Data
@Alias("myinfo")
public class MyInfoVo {
	
	int no;
	int mem_idx;
	int addr_idx;
	int zipcode;
	String addr_street;
	String addr_detail;

	

	
	
		
	

	
}
