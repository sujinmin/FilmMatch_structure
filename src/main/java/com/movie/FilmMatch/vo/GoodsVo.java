package com.movie.FilmMatch.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

/**
 * goods에 대한 데이터 객체
 */

@Data
@Alias("goods")
public class GoodsVo {

	private int goods_idx;
    private int mem_idx;
    private int cate_id;
    private String goods_name;
    private String goods_contents;
    private int goods_price;
    private int goods_rate;
    private int goods_rate_price;
    private int goods_quantity;
    private String release_date;
    private String goods_brand;
    private String image_url;
    private int goods_rating;
    private int goods_reviews;
    private int goods_availability;
    private String sale_start_date;
    private String sale_end_date;
    private int able_minors_buy;
    private String country;
    private String reg_date;
    private String mod_date;
    private int no;

}