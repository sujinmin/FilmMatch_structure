package com.movie.FilmMatch.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.movie.FilmMatch.vo.PaymentVo;
import com.movie.FilmMatch.vo.TossPayVo;

@Mapper
public interface PaymentDao {

    int insert_payment_success(Map<String, Object> map);

    /**결제내역 조회 */
    List<TossPayVo> select_list_paymemt(int mem_idx);

    /**결제 상세내역 */
    List<PaymentVo> payment_select_orderid(String orderId);

    PaymentVo payment_select_orderid_amount(String orderId);


}
