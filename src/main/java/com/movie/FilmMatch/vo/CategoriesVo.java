package com.movie.FilmMatch.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("categories")
public class CategoriesVo {

   private int        cate_id;         //카테고리 기본키
   private String     cate_sub;        //카테고리명
   private String     cate_name;       //상위 카테고리 명
   private int        cate_parent;     //부모 카테고리
   
}
