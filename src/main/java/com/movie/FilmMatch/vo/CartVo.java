package com.movie.FilmMatch.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("cart") 
public class CartVo {

   private int goods_idx;
   private int cart_idx;
   private int mem_idx;
   private int product_count;
   private int is_deleted;
   private String goods_name;
   private int goods_price;
   private int goods_rate_price;
   private int goods_count_rate_price;
   private int goods_count_price;
   private String goods_brand;
   private String image_url;
   private String country;

  

}
