package com.movie.FilmMatch.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.FilmMatch.dao.PaymentDao;
import com.movie.FilmMatch.vo.PaymentVo;
import com.movie.FilmMatch.vo.TossPayVo;

@Service
public class PaymentServiceImpl implements PaymentService {

  @Autowired
  PaymentDao payment_dao;


    @Override
      public TossPayVo convertJsonToPaymentVo(String jsonStr) throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
         // 알려지지 않은 속성을 무시하도록 설정 json 데이터에는 있지만, 사용하지 않을 데이터, 즉 VO객체에 정의되지 않은 데이터는 무시.
          objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
         
        return objectMapper.readValue(jsonStr, TossPayVo.class);
    }

      /**
     * 결제성공시 db에 해당정보 저장
     * @param map
     * @return
     */
    public int insert_payment_success(Map<String, Object> map){
      int res=payment_dao.insert_payment_success(map);

      return res;
    }


    /**결제내역 조회 */
      @Override
      public List<TossPayVo> select_list(int mem_idx) {
        List<TossPayVo> list=payment_dao.select_list_paymemt(mem_idx);
       
        return list;
      }

      /**결제 상세보기
     * @param payment_idx
     * @return
     * */
    @Override
    public List<PaymentVo> payment_select_orderid(String orderId) {
      List<PaymentVo> list=payment_dao.payment_select_orderid(orderId);
      return list;
    }

    /**
     * 결제상세 총 금액
     */
      @Override
      public PaymentVo payment_select_orderid_amount(String orderId) {
      
      PaymentVo vo=payment_dao.payment_select_orderid_amount(orderId);

      return vo;
      }



}
