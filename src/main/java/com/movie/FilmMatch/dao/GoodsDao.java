package com.movie.FilmMatch.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.movie.FilmMatch.vo.GoodsVo;

@Mapper
public interface GoodsDao {


    /** 상품리스트 조회 */
    public List<GoodsVo> selectList();
    /**상품 리스트 조회 (페이징 포함)
     * @param page_map */
    public List<GoodsVo> selectList_paging(Map<String, Object> page_map);

    public GoodsVo selectOne(int goods_idx);
    /**상품 등록 */
    public int insert(GoodsVo vo);
    /**상품 삭제*/
    public int delete(int goods_idx);
    /**상품 수정 */
    public int update(GoodsVo vo);
    /** 사진파일 수정(db상에서 사진명 만을 수정)*/
    public void update_image_url(GoodsVo vo);
    /** 현재 검색된 굿즈 상품 수 */
    public int selectRowTotal(Map<String, Object> page_map);

    

}
