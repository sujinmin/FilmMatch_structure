package com.movie.FilmMatch.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.movie.FilmMatch.vo.CategoriesVo;
import com.movie.FilmMatch.vo.GoodsVo;

/**
 * 굿즈 상품에 대한 서비스 객체
 */
@Service
public interface GoodsService {
 /** 굿즈 리스트 출력 
 * @param page_map */
 public Map<String,List<GoodsVo>> select_list(Map<String, Object> page_map);

 /**
  * 페이징 없는 메인화면 굿즈상품 출력
  * @return
  */
 public Map<String,List<GoodsVo>> select_list();
 public Map<String,List<CategoriesVo>> select_list_categories( Map<String,Object> map);

 public GoodsVo selectOne(int goods_idx);

 /** 선택된 소분류값의 부모값(상위 대분류코드) 구하기 */
 public int  isSelected_sub_cate_parent_id(int cate_sub_id);

 /** 굿즈 등록 
  * 재고 테이블에 
  데이터를 어찌넣어야 할까 고민 중입니다 
 */
 public int goods_insert(GoodsVo vo);   
 /**굿즈 삭제(상품 전부삭제? 아니면 삭제만 하고 재고는 그대로?) */
 public int goods_delete(int goods_idx);
 /** 굿즈 정보 수정할 때, 재고량도 추가해야 할까? */
 public int goods_update(GoodsVo vo);
 /**사진파일 수정 */
public void update_image_url(GoodsVo vo);

/** 현재 검색된 상품 리스트 수 */
public int selectRowTotal(Map<String,Object> page_map);  
 


}
