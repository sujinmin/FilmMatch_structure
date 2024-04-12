package com.movie.FilmMatch.controller;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.movie.FilmMatch.service.CartService;
import com.movie.FilmMatch.service.PaymentService;
import com.movie.FilmMatch.vo.CartVo;
import com.movie.FilmMatch.vo.MemberVo;
import com.movie.FilmMatch.vo.PaymentVo;
import com.movie.FilmMatch.vo.TossPayVo;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/payment/")
public class PaymentController {

    @Autowired
    PaymentService payment_service;

    @Autowired
    CartService cart_service;

    /**
     * Toss 결제 후 후처리
     * @param model
     * @param orderId
     * @param amount
     * @param paymentKey
     * @param cart_idx
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "success.do") 
    public String paymentResult(
            Model model,
            @RequestParam(value = "orderId") String orderId,
            @RequestParam(value = "amount") Integer amount,
            @RequestParam(value = "paymentKey") String paymentKey,
            @RequestParam(value = "deliveryFee") int deliveryFee,
            @RequestParam(value = "cart_idx") int[] cart_idx,
            String pay_zipcode,
            String pay_addr,
            String pay_name,
            HttpServletRequest request

    ) throws Exception {

        MemberVo member = (MemberVo) request.getSession().getAttribute("user");

        String secretKey = "test_sk_LkKEypNArWlakgEdXvJQ8lmeaxYG:";
        Boolean isError=false;
        // Base64를 사용하여 비밀 키를 인코딩
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode(secretKey.getBytes("UTF-8"));
        String authorizations = "Basic " + new String(encodedBytes, 0, encodedBytes.length); // 인증 헤더 값 생성

        // 결제 API를 위한 URL 생성
        URL url = new URL("https://api.tosspayments.com/v1/payments/" + paymentKey);

        // HTTP 연결 열기
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations); // 인증 헤더 설정
        connection.setRequestProperty("Content-Type", "application/json"); // 컨텐츠 유형 설정
        connection.setRequestMethod("POST"); // POST 메서드 사용
        connection.setDoOutput(true); // 출력 허용

        // orderId와 amount를 포함한 JSON 객체 생성
        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);

        // JSON 객체를 연결 출력 스트림에 쓰기
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes("UTF-8"));

        // 연결에서 응답 코드 가져오기
        int code = connection.getResponseCode();
        boolean isSuccess = code == 200 ? true : false; // 요청이 성공했는지 확인 (상태 코드가 200인지)
        model.addAttribute("isSuccess", isSuccess); // isSuccess 속성을 모델에 추가

        // 성공 상태에 따라 응답 스트림 가져오기
        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        // 응답 JSON 읽기
        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();

        // 응답 속성을 모델에 추가
        model.addAttribute("responseStr", jsonObject.toJSONString());
        // System.out.println(jsonObject.toJSONString());
        model.addAttribute("method", (String) jsonObject.get("method"));
        model.addAttribute("orderName", (String) jsonObject.get("orderName"));

        // 다양한 결제 방법을 처리하고, 해당하는 속성을 모델에 추가
        if (((String) jsonObject.get("method")) != null) {
            if (((String) jsonObject.get("method")).equals("카드")) {
                model.addAttribute("cardNumber", (String) ((JSONObject) jsonObject.get("card")).get("number"));
            } else if (((String) jsonObject.get("method")).equals("계좌이체")) {
                model.addAttribute("bank", (String) ((JSONObject) jsonObject.get("transfer")).get("bank"));
            } else if (((String) jsonObject.get("method")).equals("휴대폰")) {
                model.addAttribute("customerMobilePhone",
                        (String) ((JSONObject) jsonObject.get("mobilePhone")).get("customerMobilePhone"));
            }
        } else {
            model.addAttribute("code", (String) jsonObject.get("code"));
            model.addAttribute("message", (String) jsonObject.get("message"));
            isError=true;
            // ALREADY_PROCESSED_PAYMENT : 이미 결제완료
        }


        //결제 에러 발생하지 않으면 데이터 저장
        if(!isError){
        TossPayVo tosspayvo = payment_service.convertJsonToPaymentVo(jsonObject.toString());
        //배송비 추가
        tosspayvo.setDeliveryFee(deliveryFee);

        List<CartVo> list = cart_service.select_list(member.getMem_idx(), cart_idx);
        int res=cart_service.cart_delete_select(cart_idx);

        Map<String, Object> map = new HashMap<>();

        // 토스에서 넘어온 json 정보 넣기 
        map.put("tosspayvo", tosspayvo);
        map.put("pay_zipcode",pay_zipcode);
        map.put("pay_addr",pay_addr);
        map.put("pay_name",pay_name);
        for (int i = 0; i < list.size(); i++) {
            CartVo cartvo = list.get(i);

            // 결제한 상품정보 넣기
            map.put("cartvo", cartvo);
             res=payment_service.insert_payment_success(map);

           }
        }
        return "redirect:payment_list.do"; // "success" 뷰 이름 반환
    }

    /**
     * 결제 실패 시 실행
     * @param model
     * @param message
     * @param code
     * @return
     * @throws Exception
     */
    @GetMapping(value = "fail.do")
    public String paymentResult(
            Model model,
            @RequestParam(value = "message") String message,
            @RequestParam(value = "code") Integer code) throws Exception {

        model.addAttribute("code", code);
        model.addAttribute("message", message);

        return "/payment/payment_fail";
    }


    /**
     * 결제내역 리스트
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("payment_list.do")
    public String payment_list(HttpServletRequest request, Model model){
    
        
        MemberVo member = (MemberVo) request.getSession().getAttribute("user");

        // 로그아웃 상태
        if (member == null) {
            return "redirect:../member/login_form.do?reason=session_timeout";
            // session_timeout: 세션만료(로그아웃상태)

        }

        List<TossPayVo> list=payment_service.select_list(member.getMem_idx());
       model.addAttribute("list", list);


        return "/payment/payment_list";
    }

    /**
     * 주문상세내역
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("payment_select_orderid.do")
    public String payment_select_orderid(HttpServletRequest request,Model model,String orderId){
        MemberVo member = (MemberVo) request.getSession().getAttribute("user");

        // 로그아웃 상태
        if (member == null) {
            return "redirect:../member/login_form.do?reason=session_timeout";
        }

        List<PaymentVo> list=payment_service.payment_select_orderid(orderId);
        PaymentVo totalAmount=payment_service.payment_select_orderid_amount(orderId);
        model.addAttribute("list", list);
        model.addAttribute("totalAmount", totalAmount);



        return "/payment/payment_view";
    }

}