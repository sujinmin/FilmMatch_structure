package com.movie.FilmMatch.vo;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

/**
 * 결제 내역 Vo
 */
@Data
@Alias("payment")
public class PaymentVo {
    private int payment_idx;                 // 결제 고유 번호
    private int goods_idx;                   // 상품 고유 번호
    private int mem_idx;                     // 회원 고유 번호
    private int product_count;               // 구매한 상품의 수량
    private String goods_name;               // 상품 이름
    private int goods_price;                 // 상품 가격
    private int goods_rate_price;            // 상품 할인 가격
    private int goods_count_rate_price;      // 수량에 따른 총 할인 가격
    private int goods_count_price;           // 수량에 따른 총 상품 가격
    private String goods_brand;              // 상품 브랜드
    private String pay_zipcode;              // 결제자 우편번호
    private String pay_addr;                 // 결제자 주소
    private String pay_name;                 // 결제자 이름
    private String image_url;                // 상품 이미지 URL
    private String country;                  // 상품 출시 국가
    private String orderId;                  // 주문 ID
    private boolean isPartialCancelable;     // 부분 취소 가능 여부
    private int suppliedAmount;              // 공급된 금액
    private String type;                     // 결제 유형
    private String currency;                 // 사용 화폐 단위
    private String method;                   // 결제 방법
    private int vat;                         // 부가가치세
    private int deliveryFee;                 // 배송비
    private Date approvedAt;                 // 결제 승인 시간
    private int totalAmount;                 // 총 결제 금액
    private String status;                   // 결제 상태
}