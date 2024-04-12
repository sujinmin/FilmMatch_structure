package com.movie.FilmMatch.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.movie.FilmMatch.vo.CartVo;

@Mapper
public interface CartDao {

    /** 장바구니 조회*/
    public List<CartVo> selectList(int mem_idx);

    /** 사용자가 장바구니 화면에서 선택한 상품만을 조회 */
    public List<CartVo> selectList_selected(Map<String,Object> map);

    /**사용자가 선택한 상품의 배송비를 제외한 전체 총금액 */
    public int select_list_total_amount_all(Map<String, Object> map);

    /**해당 굿즈 상품이 카트에 이미 등록되어 있는가? */
    public CartVo selectOne_goods_idx(Map<String,Object> map);

    /** 장바구니 등록 */
    public int insert(CartVo vo);

    /**장바구니 삭제 */
    public int cart_delete_select(int cart_idx);

    /**관리자에 의한 상품 삭제(업데이트처리임) */
    public int goods_admin_delete(int goods_idx);

    /** 사용자에게 삭제여부 통보 후 실제 카트정보 삭제 */
    public int goods_admin_delete_real(int mem_idx);




    
}
