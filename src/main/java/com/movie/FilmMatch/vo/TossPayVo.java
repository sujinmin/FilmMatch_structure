package com.movie.FilmMatch.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("tosspay")
public class TossPayVo {

    // 결제번호
    private int payment_idx;

    // 거래가 이루어진 국가를 나타내는 코드 (예: "KR"은 대한민국)
    private String country;
    
    // 거래를 식별하기 위한 고유한 주문 번호
    private String orderId;
    
    // 거래가 부분 취소 가능한지 여부 (true: 가능, false: 불가능)
    private boolean isPartialCancelable;
    
    // 상품이나 서비스의 순수한 가격, 즉 공급가액
    private int suppliedAmount;
    
    // 거래의 유형 (예: "NORMAL"은 일반적인 거래)
    private String type;
    
    // 거래에 사용된 화폐의 단위 (예: "KRW"는 대한민국 원화)
    private String currency;
    
    // 결제 방법 (예: "간편결제"는 간편 결제 방식 사용)
    private String method;
    
    // 부가가치세 금액
    private int vat;
    
    // 배송비 금액
    private int deliveryFee;

    // 결제가 승인된 정확한 시간 (ISO 8601 형식)
    private String approvedAt;
    
    // 총 결제 금액
    private int totalAmount;
    
    // 거래의 현재 상태 (예: "DONE"은 거래 완료)
    private String status;





}
