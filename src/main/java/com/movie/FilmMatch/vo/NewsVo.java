package com.movie.FilmMatch.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("news")
public class NewsVo {

	String title;
	String originallink;
	String link;
	String description;
	String pubDate;
	
	public NewsVo(String title, String originallink, String link, String description, String pubDate) {
		this.title = title;
		this.originallink = originallink;
		this.link = link;
		this.description = description;
		this.pubDate = pubDate;
	}
	
	
}
