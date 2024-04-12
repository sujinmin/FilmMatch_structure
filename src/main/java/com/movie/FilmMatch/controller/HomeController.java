package com.movie.FilmMatch.controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movie.FilmMatch.dao.MemberDao;
import com.movie.FilmMatch.service.GoodsService;
import com.movie.FilmMatch.util.IdAPIUtils;
import com.movie.FilmMatch.util.MyKakaoUtils;
import com.movie.FilmMatch.vo.ActorVo;
import com.movie.FilmMatch.vo.DetailMovieTVVo;
import com.movie.FilmMatch.vo.GoodsVo;
import com.movie.FilmMatch.vo.NationVo;
import com.movie.FilmMatch.vo.NewsVo;
import com.movie.FilmMatch.vo.PlayingVo;
import com.movie.FilmMatch.vo.PosterVo;
import com.movie.FilmMatch.vo.TheaterVo;
import com.movie.FilmMatch.vo.VedioVo;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController{

 	@Autowired
    GoodsService goods_service;
	    
	@Autowired
	MemberDao member_dao;

    @Autowired
    ServletContext application;

	@Autowired
    HttpServletRequest request;

	@Autowired
    HttpServletResponse response;

	@Autowired
	HttpSession session;
	
	
	public HomeController() {
		System.out.println("--HomeController()--");
	}
	

	@RequestMapping("/test.do")
	@ResponseBody
	public String test(Model model){

		return "test";
	}

	/**
	 * 메인 화면
	 * @return
	 */
	@RequestMapping("/index.do")
	public String index(Model model){
		
		List<NewsVo> list = null;

	
		try {
			list =NewsAPIController.search_news();
		

		} catch (Exception e) {
			
			e.printStackTrace();
		}

	

		List<PosterVo> posterlist = null;
		try {
			posterlist =PosterAPIController.search_poster();
			

		} catch (Exception e) {
			
			e.printStackTrace();
		}

	
		List<PosterVo> votelist = null;
		try {
			votelist = PosterAPIController.search_vote();
			

		} catch (Exception e) {
			
			e.printStackTrace();
		}

	
		List<VedioVo> vediolist = null;
		try {
			vediolist =  VedioAPIController.search_vedio();
			

		} catch (Exception e) {
			
			e.printStackTrace();
		}


		Map<String, List<GoodsVo>> map = null;
		try {
			map = goods_service.select_list();
		

		} catch (Exception e) {
			
			e.printStackTrace();
		}

        model.addAttribute("map", map);
		model.addAttribute("vediolist", vediolist);
		model.addAttribute("votelist", votelist);
		model.addAttribute("posterlist", posterlist);
		model.addAttribute("newslist", list);
		
		return "main/index";
		
	}


	/**
	 * 관리자 페이지
	 * @return
	 */
	@RequestMapping("/manager_form.do")
	public String manager() {

		return "admin/manager_form";

	}

		/**
	 * 장르별 페이지
	 * @return
	 */
	@RequestMapping("/genre.do")
	public String genre(Model model) {

		List<PlayingVo> list = null;
		try {
			list = genreAPIController.search_playing();
			
	
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		

		model.addAttribute("list", list);

		return "main/genre";

	}

		/**
	 * 배우별 페이지
	 * @return
	 */
	@RequestMapping("/actor.do")
	public String actor(Model model) {

		List<ActorVo> list = null;
		try {
			list = ActorAPIController.search_actor();
			
	
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		List<ActorVo> list1 = null;
		try {
			list1 = Actor1APIController.search_actorameri();
			
	
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		List<ActorVo> list2 = null;
		try {
			list2 = Actor2APIController.search_actorjapan();
		
	
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		model.addAttribute("list2", list2);
		model.addAttribute("list1", list1);
		model.addAttribute("list", list);
	
		return "main/actor";

	}

		/**
	 * 나라별 페이지
	 * @return
	 */
	@RequestMapping("/nation.do")
	public String nation(Model model) {

		List<NationVo> list = null;
		try {
			list = NationAPIController.search_nation();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		model.addAttribute("list", list);

		return "main/nation";

	}

		/**
	 * 극장 페이지
	 * @return
	 */
	@RequestMapping("/theater.do")
	public String theater() {

		
		// List<TheaterVo> list = null;
		// try {
			
		// 	list = MyKakaoUtils.search_map(latitude, longitude, query);
		// } catch (Exception e) {		
		// 	e.printStackTrace();
		// }
		
		// model.addAttribute("list", list);

		return "main/theater";

	}

	@RequestMapping("/theater_data.do")
	@ResponseBody
	public List<TheaterVo> theater_data(String query,double latitude,double longitude) {

		
		List<TheaterVo> list = null;
		try {
			
			list = MyKakaoUtils.search_map(latitude, longitude, query);
		} catch (Exception e) {		
			e.printStackTrace();
		}
		
		return list;

	}

		/**
	 * 영화 상세 페이지
	 * @return
	 */
	@RequestMapping("/movieinfo.do")
	public String movieinfo(String id,String media_type,Model model) {



		List<DetailMovieTVVo> list = null;
		try {
			
			list = IdAPIUtils.search_id(id,media_type);
			
		} catch (Exception e) {		
			e.printStackTrace();
		}

		model.addAttribute("list", list);
		return "main/movieinfo";

	}

}
