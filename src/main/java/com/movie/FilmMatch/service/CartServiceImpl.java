package com.movie.FilmMatch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.FilmMatch.dao.CartDao;
import com.movie.FilmMatch.vo.CartVo;
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartDao cart_dao;

    /**
     * 굿즈상품 장바구니 조회
     */
    @Override
    public List<CartVo> select_list(int mem_idx) {


        List<CartVo> list=cart_dao.selectList(mem_idx);
        

        return list;
        
    }

    /**
     * 사용자가 장바구니에서 선택한 상품만을 조회
     */
    @Override
    public List<CartVo> select_list(int mem_idx, int[] cart_idx) {
      
        Map<String,Object> map=new HashMap<>();
        map.put("mem_idx",mem_idx);
        map.put("cart_idx",cart_idx);
        List<CartVo> payment_list=cart_dao.selectList_selected(map);

        return payment_list; 
    }


    /**사용자가 선택한 상품의 전체 총금액(배달비 제외) */
    @Override
    public int select_list_total_amount_all(int mem_idx, int[] cart_idx) {
        Map<String,Object> map=new HashMap<>();
        map.put("mem_idx",mem_idx);
        map.put("cart_idx",cart_idx);
       int total_amount_all=cart_dao.select_list_total_amount_all(map);
       return total_amount_all;
    }


    /*
     * 굿즈 등록
     */
    @Override
    public int insert(CartVo vo) {

        int res=0;
        //상품이 이미 담겨있어 조회가 되는가?
        Map<String,Object> map=new HashMap<>();
        map.put("goods_idx", vo.getGoods_idx());
        map.put("mem_idx",vo.getMem_idx());

        CartVo result_vo=cart_dao.selectOne_goods_idx(map);

        /*
         * res
         * 1 = 상품등록  성공
         * 0 = 상품등록  실패
         * 2 = 상품 이미 존재
         */
        if(result_vo!=null){
          
            return 2;
        }
        
        res= cart_dao.insert(vo);

        
        return res;
    }

    /**카트 항목 제거 */
    @Override
    public int cart_delete_select(int[] cart_idx) {
        int res=0;

        for(int i=0;i<cart_idx.length;i++){
            res=cart_dao.cart_delete_select(cart_idx[i]);
        }

        return res;
    }

    /**관리자에 의한 상품 삭제처리로 인한 카트정보 삭제처리 */
    @Override
    public int cart_admin_delete_goods_idx_real(int mem_idx) {
  
         int   res=cart_dao.goods_admin_delete_real(mem_idx);
       
       

       return res;
    }

   
   

}
