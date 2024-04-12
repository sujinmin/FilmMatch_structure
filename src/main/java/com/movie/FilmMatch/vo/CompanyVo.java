package com.movie.FilmMatch.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("company") 
public class CompanyVo {


   String logo_path;
   String name;
   String origin_country;


   public CompanyVo( String logo_path, String name, String origin_country) {
     
      this.logo_path = logo_path;
      this.name = name;
      this.origin_country = origin_country;
   }

   

}
