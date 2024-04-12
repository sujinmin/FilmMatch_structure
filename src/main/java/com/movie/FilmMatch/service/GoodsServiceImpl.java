package com.movie.FilmMatch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.FilmMatch.dao.CartDao;
import com.movie.FilmMatch.dao.CategoriesDao;
import com.movie.FilmMatch.dao.GoodsDao;
import com.movie.FilmMatch.vo.CategoriesVo;
import com.movie.FilmMatch.vo.GoodsVo;
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsDao goods_dao;

    @Autowired
    CartDao cart_dao;

    @Autowired
    CategoriesDao categories_dao;



    /**
     * 상품리스트 (낫 페이징)
     */
    @Override
    public Map<String, List<GoodsVo>> select_list() {
        Map<String,List<GoodsVo>> map=new HashMap<>();
        List<GoodsVo> goods_contents=goods_dao.selectList();
        map.put("goods_contents", goods_contents);
        return map;
    }

    /**
     * 상품리스트(페이징)
     */
    @Override
    public Map<String,List<GoodsVo>> select_list(Map<String, Object> page_map) {   
        Map<String,List<GoodsVo>> map=new HashMap<>();
        
        List<GoodsVo> goods_contents=goods_dao.selectList_paging(page_map);
        map.put("goods_contents", goods_contents);
    
        return map;
    }


    /**
     * 현재 검색된 굿즈 상품 수
     */
    @Override
    public int selectRowTotal(Map<String, Object> page_map) {

        int rowTotal=goods_dao.selectRowTotal(page_map);
        return rowTotal;
    }


    /**
     * 상품 상세보기
     */
    @Override
    public GoodsVo selectOne(int goods_idx) {
        GoodsVo vo=goods_dao.selectOne(goods_idx);
        return vo;
    }

    /**
     * 재고 테이블에 직빵으로 집어넣을 지, 아니면 후처리할지는...흐음 나중에 보자
     */
    @Override
    public int goods_insert(GoodsVo vo) {
        int res=goods_dao.insert(vo);
        return res;
    }

    /**
     * 상품 삭제
     */
    @Override
    public int goods_delete(int goods_idx) {
        
        int res=goods_dao.delete(goods_idx);

        /**관리자에 의해 상품 삭제처리(업데이트 is_deleted=1) */
        res=cart_dao.goods_admin_delete(goods_idx);





        return res;
    }

    /**
     * 상품 수정
     */
    @Override
    public int goods_update(GoodsVo vo) {
        int res =goods_dao.update(vo);
        return res;
    }

    /**
     * 카테고리 분류 조회
     */
    @Override
    public Map<String, List<CategoriesVo>> select_list_categories(Map<String,Object> map) {

        // 리스트 결과값을 담을 map
        Map<String, List<CategoriesVo>> result_map=new HashMap<>();
        
        //대분류를 찾는가?
        boolean isTopLevel= (boolean) map.get("isTopLevel");

        //System.out.println("isTopLevel:"+isTopLevel);

        //대분류값을 찾는경우
        if(isTopLevel){
        //최상단 레코드만을 담음
        List<CategoriesVo> cate_top_list=categories_dao.selectList(map);
        // List<CategoriesVo> cate_sub_list=categories_dao.selectList_sub();

        result_map.put("cate_top_list", cate_top_list);
        }
        else{// 소 분류 값을 찾는 경우
            List<CategoriesVo> cate_sub_list=categories_dao.selectList(map);
            // System.out.println("cate_sub_list:"+cate_sub_list.size());
            result_map.put("cate_sub_list", cate_sub_list);
        }
       // map.put("cate_sub_list", cate_sub_list);
        
        return result_map;
    }

    /** 선택된 소분류값의 부모값(상위 대분류코드) 구하기 */
    @Override
    public int isSelected_sub_cate_parent_id(int cate_sub_id) {
    
     
        /** 서브카테고리 값을 기준으로, 해당 카테고리의 부모 카테고리 즉,대분류값을 구한다. */
        int cate_main_id=categories_dao.isSelected_sub_cate_parent_id(cate_sub_id);

       return cate_main_id;
    }

    /**
     * 사진파일 수정(db상에서 사진 명만을 수정)
     */
    @Override
    public void update_image_url(GoodsVo vo) {
       
        goods_dao.update_image_url(vo);
        
    }

  
   
}
