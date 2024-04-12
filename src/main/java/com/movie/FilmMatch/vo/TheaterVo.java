package com.movie.FilmMatch.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("Theater")
public class TheaterVo {

   String category_group_code;
   String address_name;
   String distance;
   String id;
   String phone;
   String place_name;
   String place_url;
   String road_address_name;
   double x;
   double y;

   public TheaterVo() {

   }

   public TheaterVo(String category_group_code, String address_name, String distance, String id, String phone,
         String place_name, String place_url, String road_address_name, double x, double y) {
      this.category_group_code = category_group_code;
      this.address_name = address_name;
      this.distance = distance;
      this.id = id;
      this.phone = phone;
      this.place_name = place_name;
      this.place_url = place_url;
      this.road_address_name = road_address_name;
      this.x = x;
      this.y = y;
   }



  

}
