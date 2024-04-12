package com.movie.FilmMatch.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movie.FilmMatch.dao.CartDao;
import com.movie.FilmMatch.service.CartService;
import com.movie.FilmMatch.vo.CartVo;
import com.movie.FilmMatch.vo.MemberVo;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/cart/")
public class CartController {

    @Autowired
    CartService cart_service;

    @Autowired
    CartDao cart_dao;

    /**
     * 카트 이동
     */
    @RequestMapping("cart_form.do")
    public String cart_form(HttpServletRequest request, Model model) {

        MemberVo member = (MemberVo) request.getSession().getAttribute("user");

        // 로그아웃 상태
        if (member == null) {
            return "redirect:../member/login_form.do?reason=session_timeout";
            // session_timeout: 세션만료(로그아웃상태)

        }

        int is_deleted = 0;
        List<CartVo> list = cart_service.select_list(member.getMem_idx());

        /** 관리자에 의해 상품이 삭제된 정보가 있는가? */
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIs_deleted() == 1) {

                is_deleted = 1;
                /** 삭제된 상품정보를 카트에 담아둔 사용자에게 삭제통보처리 */
                model.addAttribute("is_deleted", is_deleted);
                /**관리자에 의해 삭제된 상품정보를 담은 회원의 카트정보 제거 */
                cart_service.cart_admin_delete_goods_idx_real(member.getMem_idx());
                // 삭제 후 다시 조회
                 list = cart_service.select_list(member.getMem_idx());
                break;
            }

        }


        model.addAttribute("list", list);

        return "cart/cart_form";
    }

    /**
     * 장바구니 담기
     * 
     * @param goods_idx
     * @param mem_idx
     * @return
     */
    @RequestMapping("cart_insert.do")
    @ResponseBody
    public String cart_insert(HttpServletRequest request, int goods_idx, int product_count) {

        MemberVo member = (MemberVo) request.getSession().getAttribute("user");
        String result = "faile";
        CartVo vo = new CartVo();
        vo.setGoods_idx(goods_idx);
        vo.setMem_idx(member.getMem_idx());
        //System.out.println(member.getMem_idx());
        vo.setProduct_count(product_count);

        int res = cart_service.insert(vo);
        if (res == 2) {

            result = "exist";
        } else {
            result = "success";
        }

        JSONObject json = new JSONObject();
        json.put("result", result); // {"result":"success"} or {"result":"exist"}

        return json.toString();
    }

    /**
     * 상품 삭제
     * @param cart_idx
     * @return
     */
    @RequestMapping("cart_delete_select.do")
    public String cart_delete_select(int[] cart_idx) {

        cart_service.cart_delete_select(cart_idx);

        return "redirect:cart_form.do";
    }



    @RequestMapping("payment_form.do")
    public String payment_form(HttpServletRequest request,int[] cart_idx,Model model){

        
        MemberVo member = (MemberVo) request.getSession().getAttribute("user");

        // 로그아웃 상태
        if (member == null) {
            return "redirect:../member/login_form.do?reason=session_timeout";
            // session_timeout: 세션만료(로그아웃상태)

        }

        List<CartVo> payment_list = cart_service.select_list(member.getMem_idx(),cart_idx);

        // 결제 총 금액(배달비 제외)
        int total_amount_all=cart_service.select_list_total_amount_all(member.getMem_idx(),cart_idx);
        model.addAttribute("payment_list", payment_list);
        model.addAttribute("total_amount_all", total_amount_all);

        return"/payment/payment_form";
    }
    

}
