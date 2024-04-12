package com.movie.FilmMatch.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.movie.FilmMatch.vo.CartVo;

@Service
public interface CartService {

    /**장바구니 조회 */
    public List<CartVo> select_list(int mem_idx);

    /** 사용자가 장바구니에서 선택한 상품만을 조회 */
    public List<CartVo> select_list(int mem_idx, int[] cart_idx);

    /**선택한 상품의 전체 총금액(배달비 제외) */
    public int select_list_total_amount_all(int mem_idx, int[] cart_idx);
    /**장바구니 등록 */
    public int insert(CartVo vo);

    /**장바구니 항목 삭제 */
    public int cart_delete_select(int[] cart_idx);


    /** 관리자에 의해 삭제된 상품정보로 인한 카트정보 삭제처리 */
    public int cart_admin_delete_goods_idx_real(int mem_idx);

  




}