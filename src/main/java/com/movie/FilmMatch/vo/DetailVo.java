package com.movie.FilmMatch.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("detail") 
public class DetailVo {

String budget;
String homepage; 
String original_title;
String overview;
String popularity;
String poster_path;
String release_date; 
String revenue;
String runtime;
String vote_average;
String vote_count;

}
