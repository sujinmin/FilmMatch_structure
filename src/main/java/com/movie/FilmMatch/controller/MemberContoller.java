package com.movie.FilmMatch.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.movie.FilmMatch.dao.MemberDao;
import com.movie.FilmMatch.service.EmailService;
import com.movie.FilmMatch.vo.MemberVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/member/") //url앞부분에 매번 요청됨
public class MemberContoller {

	@Autowired
	MemberDao member_dao;
	
	@Autowired
	HttpSession session;
	
	


	/**
	 * 로그인 폼 띄우기
	 * @return
	 */
	@RequestMapping("login_form.do")
	public String login_form() {
		
		return "member/login_form";
	}
	//login
	/**
	 * 로그인
	 * @param mem_id
	 * @param mem_pwd
	 * @param redirect_attr
	 * @return
	 */
	@RequestMapping("login.do")
	public String login(String mem_id,String mem_pwd,
						RedirectAttributes redirect_attr) {
		
		//2.mem_id에 해당되는 유저정보를 읽어오기
		MemberVo  user = member_dao.selectOneFromId(mem_id);
		
		//아이디가 틀린경우
		if (user==null) {
			// response.sendRedirect("login_form.do?reason=fail_id");
			//Spring boot에서는 명확하게 구분된다.
			//Model  1. forward -> request binding
			//		 2. rediect -> paramerter 사용
			redirect_attr.addAttribute("reason", "fail_id");
			return "redirect:login_form.do?reason=fail_id";
		}
		//비밀번호가 틀린경우
		if(user.getMem_pwd().equals(mem_pwd)==false) {
			//response.sendRedirect("login_form.do?reason=fail_pwd&mem_id=" + mem_id);
			redirect_attr.addAttribute("reason", "fail_pwd");
			redirect_attr.addAttribute("mem_id",mem_pwd);
			return "redirect:login_form.do?reason=fail_pwd&mem_id=";
		}
		
		//로그인 정보를 세션에 저장
		session.setAttribute("user", user);

		return "redirect:../index.do";
	}
	
	//로그아웃
	@RequestMapping("logout.do")
	public String logout() {
		
		//세션에서 로그인 정보 삭제
		session.removeAttribute("user");
		
		return "redirect:../index.do";
	}
	/**
	 * 약관동의 폼 띄우기
	 * @return
	 */
	@RequestMapping("policy.do")
	public String policy() {

		return "member/policy";
	}
	

	//회원가입 폼띄우기
	@RequestMapping("signup_form.do")
	public String signup_form(@RequestParam("policy_a") String policy_a, 
							  @RequestParam("policy_b") String policy_b, 
							  MemberVo vo,
							  HttpSession session, Model model) {

		String randomCode = (String) session.getAttribute("randomCode");
		model.addAttribute("randomCode", randomCode);

		return "member/signup_form";
	}
	/**
	 * 회원가입 insert
	 * @return
	 */
	@RequestMapping("insert.do")
	public String insert( @ModelAttribute("membervo") MemberVo vo, 
								BindingResult bindingResult, 
								Model model,
								HttpServletRequest request) {

		String mem_ip = request.getRemoteAddr();
		vo.setMem_ip(mem_ip); // UserVo에 IP 주소 설정

		member_dao.insert(vo);
		
		//회원가입시 자동로그인
		MemberVo loggedInUser = member_dao.selectOneFromId(vo.getMem_id());
		if (loggedInUser != null && loggedInUser.getMem_pwd().equals(vo.getMem_pwd())) {
			// 로그인 성공 시, 세션에 사용자 정보 저장
			session.setAttribute("user", loggedInUser);
		}

		return "redirect:../index.do";
	}
	
	@RequestMapping("/check_id.do")
	@ResponseBody
    public String check_id(String mem_id) {
        MemberVo vo = member_dao.selectOneFromId(mem_id);
        System.out.println("입력된 ID:"+mem_id);
		boolean bResult = vo == null;
		if(vo!=null)
		System.out.println("저장되어있는 아이디"+vo.getMem_id());

		JSONObject json = new JSONObject();
		json.put("result", bResult); //{"result": true}
		System.out.println("결과 : "+bResult);
        return json.toString();
    }

	/**
	 * 카카오톡에서 정보를 세션에 저장해서 가져오는 방법
	 * @param userInfo
	 * @param request
	 * @return
	 */
	//  @RequestMapping(value = "/save-kakao-user-info.do", method = {RequestMethod.POST, RequestMethod.GET})
    // // @ResponseBody
    // public String saveKakaoUserInfo(@RequestBody String userInfo, 
	// 								HttpServletRequest request,
	// 								Model model) {
		
	// 	try {
	// 		System.out.println("받은 사용자 정보: " + userInfo);

	// 		// 받은 사용자 정보를 세션에 저장
	// 		HttpSession session = request.getSession();
	// 		session.setAttribute("kakaoUserInfo", userInfo);
			
	// 		// 모델에 로그인 정보를 추가하여 회원 가입 페이지로 전달
	// 		model.addAttribute("kakaoUserInfo", userInfo);

	// 		return "member/signup_form";

	// 	} catch (Exception e) {
	// 		System.err.println("에러 발생: " + e.getMessage());
	// 		throw e; // 예외를 다시 던져서 스프링이 적절하게 처리하도록 함
	// 	}
    // }
	@RequestMapping(value = "/save-kakao-user-info.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String saveKakaoUserInfo(@RequestBody(required = false) String userInfo, 
									 @RequestParam(required = false) String userInfoQueryParam,
									 HttpServletRequest request,
									 Model model) {
	
		try {
			if (userInfo != null) {
				System.out.println("받은 사용자 정보: " + userInfo);
				HttpSession session = request.getSession();
				session.setAttribute("kakaoUserInfo", userInfo);
				model.addAttribute("kakaoUserInfo", userInfo);
			} else if (userInfoQueryParam != null) {
				System.out.println("받은 사용자 정보(쿼리 파라미터): " + userInfoQueryParam);
				HttpSession session = request.getSession();
				session.setAttribute("kakaoUserInfo", userInfoQueryParam);
				model.addAttribute("kakaoUserInfo", userInfoQueryParam);
			} else {
				// 요청에 사용자 정보가 없을 경우 처리
				System.out.println("사용자 정보가 없습니다.");
			}
	
			return "member/signup_form";
		} catch (Exception e) {
			System.err.println("에러 발생: " + e.getMessage());
			throw e;
			
		}
	}

	@RequestMapping("main.do")
    public String main() {


        return "member/main";
    }
		/**
	 * 이메일 mapper
	 * @return
	 */
    @RequestMapping("/send_email.do")
	@ResponseBody
    public Map<String,String> sendEmail(String email) {

		//String randomCode = EmailService.generateRandomCode();
		
        String randomCode = EmailService.sendEmail(email);
		//session.setAttribute("randomCode", randomCode);
		//System.out.println("랜덤코드"+randomCode);
		Map<String,String> map = new HashMap<String,String>();
		map.put("randomCode", randomCode);
	

        return map;
    }
	
}
