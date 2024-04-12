package com.movie.FilmMatch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.movie.FilmMatch.dao.MemberDao;
import com.movie.FilmMatch.dao.MyInfoDao;
import com.movie.FilmMatch.service.GoodsService;
import com.movie.FilmMatch.vo.MemberVo;
import com.movie.FilmMatch.vo.MyInfoVo;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


	

@Controller
public class MyPageController{

 	@Autowired
    GoodsService goods_service;
	    
	@Autowired
	MemberDao member_dao;
	
	@Autowired
	MyInfoDao myinfo_dao;

    @Autowired
    ServletContext application;

	@Autowired
    HttpServletRequest request;

	@Autowired
    HttpServletResponse response;

	@Autowired
	HttpSession session;
	
	

	/**
	 * 마이페이지
	 * @return
	 */
	@RequestMapping("/mypage_form.do")
	public String mypage() {

		return "member/mypage_form";

	}

	/**
	 * 나의정보 페이지 (회원정보&주소목록)
	 * @return
	 */
	@RequestMapping("/mypage_myinfo.do")
	public String mypage_myinfo(MyInfoVo info_vo,
								Model model) {

		MemberVo vo = (MemberVo) request.getSession().getAttribute("user");
		System.out.println(vo);
		
		//회원정보의 주소 가져오기
		List<MyInfoVo> list = myinfo_dao.selectList(vo.getMem_idx());				
		System.out.println(list);
		

		if (vo == null) {
            return "redirect:../member/login_form.do?reason=session_timeout";
            // session_timeout: 세션만료(로그아웃상태)
		}
		
		request.setAttribute("vo", vo);
		model.addAttribute("list", list);
		//System.out.println(list);

		return "member/mypage_myinfo";
	}

	/**
	 * 회원정보 수정
	 * @param vo
	 * @param model
	 * @return
	 */
    @RequestMapping("myinfo_modify.do")
    public String modify(MemberVo vo,
                         Model model) {
                            
							 
		String mem_ip = request.getRemoteAddr();
        vo.setMem_ip(mem_ip);
        
		member_dao.update(vo);
		
        // 업데이트된 회원 정보를 다시 조회
        MemberVo updatedMember = member_dao.selectOneFromIdx(vo.getMem_idx());
		
		
        // Model에 업데이트된 회원 정보 바인딩
        model.addAttribute("vo", updatedMember);
		
        return "member/mypage_myinfo";
    }
	
	/**
	 * 주소 등록 폼띄우기
	 * @return
	 */
	@RequestMapping("mypage_myinfo_insert_form.do")
	public String mypage_myinfo_insert() {
		
		MemberVo vo = (MemberVo) request.getSession().getAttribute("user");
		System.out.println(vo);

		return "member/mypage_myinfo_insert";
	}
	
	/**
	 * 주소수정폼띄우기
	 * @param vo
	 * @return
	 */
	@RequestMapping("addr_update_form.do")
	public String addr_update_form(MyInfoVo vo,
									Model model) {
		//회원정보의 주소 가져오기
		List<MyInfoVo> list = myinfo_dao.selectList(vo.getMem_idx());				
		//System.out.println(list);

		model.addAttribute("list", list);

		return "member/mypage_myinfo_update";
	}
	/**
	 * 주소수정
	 * @param vo
	 * @return
	 */
	@RequestMapping("addr_update.do")
	public String addr_update(MyInfoVo vo) {

		myinfo_dao.update(vo);

		return "member/mypage_myinfo";
	}
	
	/**
	 * 주소삭제
	 * @param addr_idx
	 * @return
	 */
	@RequestMapping("addr_delete.do")
	public String addr_delete(int addr_idx) {

		request.getSession().getAttribute("user");

		myinfo_dao.delete(addr_idx);

		return "redirect:mypage_myinfo.do";
	}

	@RequestMapping("test_modal.do")
	public String test_modal() {

		return "member/modal";
	}
	
	


}
