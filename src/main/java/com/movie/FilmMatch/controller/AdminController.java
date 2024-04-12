package com.movie.FilmMatch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.movie.FilmMatch.dao.MemberDao;
import com.movie.FilmMatch.vo.MemberVo;

import jakarta.servlet.http.HttpServletRequest;




@Controller
@RequestMapping("/admin/")
public class AdminController {

    //회원관리페이지
   @Autowired
    MemberDao member_dao;

    @Autowired
    HttpServletRequest request;


    /**
     * 관리자페이지 메인
     * @return
     */
    @RequestMapping("main.do")
    public String admin_main() {
        
        return "admin/admin_main";
    }
    /**
     * 상품 입고 관리
     * @return
     */
    @RequestMapping("inventory_in.do")
    public String inventory_in() {
    
        
        return "admin/inventory_in";
    }
    /**
     * 상품 재고 관리
     * @return
     */
    @RequestMapping("inventory_remain.do")
    public String inventory_remain() {
    
        
        return "admin/inventory_remain";
    }
    /**
     * 상품 출고 관리
     * @return
     */
    @RequestMapping("inventory_out.do")
    public String inventory_out() {
    
        
        return "admin/inventory_out";
    }
    /**
     * 관리자 페이지 메뉴
     * @return
     */
    @RequestMapping("admin_menu.do")
    public String admin_menu() {
    
        
        return "admin/admin_menu";
    }
    /**
     * 회원관리페이지 띄우기
     * @return
     */
    @RequestMapping("admin_member.do")
    public String admin_member(Model model) {
    
        //회원정보 가져오기
		List<MemberVo> list = member_dao.selectList();

        // Model에 데이터 바인딩
        model.addAttribute("list", list);

        return "admin/member_menagement/admin_member";
    }
    @RequestMapping("view.do")
    public String view(@RequestParam int mem_idx,
                        Model model) {

        MemberVo vo = member_dao.selectOneFromIdx(mem_idx);
        
        // Model에 데이터 바인딩
        model.addAttribute("vo", vo);

        return "admin/member_menagement/admin_member_view";
    }
    

    /**
     * 회원정보 수정 불러오기
     * @param mem_idx
     * @param model
     * @return
     */
    @RequestMapping("admin_modify.do")
    public String admin_modify(@RequestParam int mem_idx,
                                Model model) {

       	//mem_id에 해당되는 유저정보를 읽어오기
        //int memIdx = Integer.parseInt(mem_idx);
        MemberVo vo = member_dao.selectOneFromIdx(mem_idx);
        
        // Model에 데이터 바인딩
        model.addAttribute("vo", vo);

        return "admin/member_menagement/admin_member_modify";
    }

    //회원정보 수정
    @RequestMapping("modify.do")
    public String modify(MemberVo vo,
                            Model model) {
                            
        String mem_ip = request.getRemoteAddr();
        vo.setMem_ip(mem_ip); // UserVo에 IP 주소 설정

        member_dao.update(vo);

        // 업데이트된 회원 정보를 다시 조회
        MemberVo updatedMember = member_dao.selectOneFromIdx(vo.getMem_idx());

        // Model에 업데이트된 회원 정보 바인딩
        model.addAttribute("vo", updatedMember);

        return "redirect:admin_member.do";
        
    }
    
    /**
     * 회원삭제
     * @param mem_idx
     * @return
     */
    @RequestMapping("delete.do")
    public String requestMethodName(@RequestParam int mem_idx) {
        
        int res = member_dao.delete(mem_idx);
        if (res==1) {
            //성공
			System.out.println("성공"+res);
		} else {
            //실패
			System.out.println("실패"+res);
		}
        return "redirect:admin_member.do";
    }
    


}
