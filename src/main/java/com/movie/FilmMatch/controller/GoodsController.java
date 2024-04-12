package com.movie.FilmMatch.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.FilmMatch.movieconstant.MovieConstant;
import com.movie.FilmMatch.service.GoodsService;
import com.movie.FilmMatch.util.Paging;
import com.movie.FilmMatch.vo.CategoriesVo;
import com.movie.FilmMatch.vo.GoodsVo;
import com.movie.FilmMatch.vo.MemberVo;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/goods/")
public class GoodsController {
    @Autowired
    GoodsService goods_service;

    @Autowired
    ServletContext application;

    /**
     * 굿즈 리스트
     * 
     * @param model
     * @return
     */
    @RequestMapping("list.do")
    public String goods_list(
            Model model,
            @RequestParam(name = "page", required = false) String page,
            @RequestParam(name = "search_option", required = false) String search_option,
            @RequestParam(name = "search_text", required = false) String search_text) {

        int nowPage = 1;

        if (search_option == null || search_option.isEmpty()) {
            search_option = "all";
        }

        // 검색어가 없을때 null이 url에 들어가는것을 방지하기 위함
        if (search_text == null) {
            search_text = "";
        }

        // 페이지를 넘긴경우
        if (page != null && !page.isEmpty()) {
            nowPage = Integer.parseInt(page);
        }

        // (start,end) 계산,nowPage는 시작 페이지 수를 계산하기위한 변수
        int start = (nowPage - 1) * MovieConstant.Goods.BLOCK_LIST + 1;
        int end = start + MovieConstant.Goods.BLOCK_LIST - 1;

        // 검색 조건 정보 map 포장
        Map<String, Object> page_map = new HashMap<String, Object>();
        page_map.put("start", start);
        page_map.put("end", end);

        if (search_option.equals("subject"))
            page_map.put("subject", search_text);

        Map<String, List<GoodsVo>> map = goods_service.select_list(page_map);
        int rowTotal = goods_service.selectRowTotal(page_map);

        String search_filter = String.format("&search_option=%s&search_text=%s", search_option, search_text);

        String pageMenu = Paging.getGoodsPaging("list.do",
                search_filter // 검색조건
                , nowPage // 현재페이지
                , rowTotal // 전체게시물수
                , MovieConstant.Goods.BLOCK_LIST // 한화면에 보여질 게시물 수
                , MovieConstant.Goods.BLOCK_PAGE); // 한화면에 보여질 메뉴의 수
        model.addAttribute("map", map);
        model.addAttribute("pageMenu", pageMenu);
        model.addAttribute("rowTotal", rowTotal);

        return "goods/goods_list";
    }

    /**
     * 결제이동
     */
    @RequestMapping("payment_form.do")
    public String payment_form(HttpServletRequest request,Model model) {

        MemberVo member = (MemberVo) request.getSession().getAttribute("user");

        // 로그아웃 상태
        if (member == null) {
            return "redirect:../member/login_form.do?reason=session_timeout";
            // session_timeout: 세션만료(로그아웃상태)

        }
        return "goods/payment_form";
    }

    /**
     * 상품수정폼
     * 
     * @param param
     * @return
     */
    @RequestMapping("update_form.do")
    public String update_form(HttpServletRequest request,int goods_idx, Model model) {
        MemberVo member = (MemberVo) request.getSession().getAttribute("user");

        // 로그아웃 상태
        if (member == null) {
            return "redirect:../member/login_form.do?reason=session_timeout";
            // session_timeout: 세션만료(로그아웃상태)

        }

        /** 카테고리 가져오기(대분류만) */
        Map<String, Object> cate_top_map = new HashMap<>();
        cate_top_map.put("isTopLevel", true);

        Map<String, List<CategoriesVo>> result_map = goods_service.select_list_categories(cate_top_map);
        model.addAttribute("result_map", result_map);

        GoodsVo goodsvo = goods_service.selectOne(goods_idx);

        // 선택된 서브 카테고리를 일단 구함
        int cate_sub_id = goodsvo.getCate_id();

        // System.out.println("cate_sub_id:"+cate_sub_id);
        // 해당 카테고리 id의 부모 id값을 구한다.
        int cate_main_id = goods_service.isSelected_sub_cate_parent_id(cate_sub_id);
        // System.out.println("cate_main_id:"+cate_main_id);

        model.addAttribute("cate_sub_id", cate_sub_id);
        model.addAttribute("cate_main_id", cate_main_id);
        model.addAttribute("goodsvo", goodsvo);

        return "goods/update_form";
    }

    /**
     * 상품 자세히 보기
     * 
     * @return
     */
    @RequestMapping("goods_view.do")
    public String goods_view(int goods_idx, Model model) {
        GoodsVo vo = goods_service.selectOne(goods_idx);

        model.addAttribute("vo", vo);
        return "goods/goods_views";
    }

    /**
     * 상품등록폼
     * 
     * @param param
     * @return
     */
    @RequestMapping("insert_form.do")
    public String insert_form(HttpServletRequest request,Model model) {

        MemberVo member = (MemberVo) request.getSession().getAttribute("user");

        // 로그아웃 상태
        if (member == null) {
            return "redirect:../member/login_form.do?reason=session_timeout";
            // session_timeout: 세션만료(로그아웃상태)

        }

        /** 카테고리 가져오기(대분류만) */
        Map<String, Object> cate_top_map = new HashMap<>();
        cate_top_map.put("isTopLevel", true);

        Map<String, List<CategoriesVo>> result_map = goods_service.select_list_categories(cate_top_map);
        model.addAttribute("result_map", result_map);

        return "goods/insert_form";
    }

    /**
     * 서브 카테고리 가져오기
     * 
     * @param cate_parent_id
     * @return
     */
    @RequestMapping("getSubcategories.do")
    @ResponseBody
    public String select_sub_categories(int cate_parent_id) {
        Map<String, Object> cate_sub_map = new HashMap<>();

        cate_sub_map.put("cate_parent_id", cate_parent_id);
        cate_sub_map.put("isTopLevel", false);
        Map<String, List<CategoriesVo>> result_map = goods_service.select_list_categories(cate_sub_map);

        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(result_map);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return json.toString();
    }

    /**
     * 상품등록
     * 
     * @param request
     * @param photo
     * @param vo
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @RequestMapping("insert.do")
    public String insert(HttpServletRequest request, @RequestParam MultipartFile photo, GoodsVo vo)
            throws IllegalStateException, IOException {
        MemberVo member = (MemberVo) request.getSession().getAttribute("user");

        // 로그아웃 상태
        if (member == null) {
            return "redirect:../member/login_form.do?reason=session_timeout";
            // session_timeout: 세션만료(로그아웃상태)

        }

        String abs_path = application.getRealPath("/upload/");
        // System.out.println(abs_path);
        String image_url = "no_file";

        if (!photo.isEmpty()) {// 업로드 된 파일 존재시

            image_url = photo.getOriginalFilename();

            // 파일정보 관리객체
            File file = new File(abs_path, image_url);

            // while이면,동일 파일 존재유무 체크(같은 이름이 나오지 않을 때 까지 반복하는 작업이나,이렇게 갈 확률은 매우매우매우매우 낮음)
            // 따라서 그냥 if문처리
            if (file.exists()) {
                long time = System.currentTimeMillis(); // 현재 시간을 milisecond 단위로 구함

                // 이름변경
                // 27384623_a.jpg
                image_url = UUID.randomUUID().toString();
                file = new File(abs_path, image_url);
                // System.out.println("같은파일존재");
            }

            // 임시저장 파일 -> 내가 지정한 위치 +파일명으로 복사
            photo.transferTo(file);
        }

        // String p_ip = request.getRemoteAddr();
        System.out.println(image_url);
        vo.setImage_url(image_url);
        goods_service.goods_insert(vo);
        return "redirect:list.do";
    }

    /**
     * 상품 수정
     * 
     * @param request
     * @param vo
     * @param old_image_url
     * @return goods/goods_views
     */
    @RequestMapping("update.do")
    public String goods_update(HttpServletRequest request,GoodsVo vo,
            @RequestParam(name = "deletedFilenames", required = false) String[] deletedFilenames,
            Model model, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {

                MemberVo member = (MemberVo) request.getSession().getAttribute("user");

                // 로그아웃 상태
                if (member == null) {
                    return "redirect:../member/login_form.do?reason=session_timeout";
                    // session_timeout: 세션만료(로그아웃상태)
        
                }

        String abs_path = application.getRealPath("/resources/ckimage/");

        goods_service.goods_update(vo);

        // 사용자가 상품 수정 할 때, 제거한 사진이 있을 경우, 해당 사진 서버에서 제거
        if (deletedFilenames != null) {
            for (int i = 0; i < deletedFilenames.length; i++) {
                File oldFile = new File(abs_path, deletedFilenames[i]);
                oldFile.delete();
            }
        }
        vo = goods_service.selectOne(vo.getGoods_idx());
        model.addAttribute("vo", vo);
        // RedirectAttributes를 사용하여 리다이렉트 URL에 goods_idx 추가
        redirectAttributes.addAttribute("goods_idx", vo.getGoods_idx());
        return "redirect:goods_view.do";
    }

    /**
     * 사진 수정
     * 
     * @param photo
     * @param goods_idx
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @RequestMapping("photo_upload.do")
    @ResponseBody
    public Map<String, Object> photo_upload(@RequestParam MultipartFile photo, int goods_idx)
            throws IllegalStateException, IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        String abs_path = application.getRealPath("/upload/");
        String image_url = "no_file";
        //System.out.println(goods_idx);

        image_url = photo.getOriginalFilename();
        // System.out.println("p_filename:"+p_filename);
        File file = new File(abs_path, image_url);
        if (file.exists()) {

            // 시간_filename 형식으로 이름 변경
            // 27384623_a.jpg
            image_url = UUID.randomUUID().toString();
            file = new File(abs_path, image_url);
            // System.out.println("같은파일존재");
        }

        // 임시저장 파일 -> 내가 지정한 위치 +파일명으로 복사
        photo.transferTo(file);
        GoodsVo vo = goods_service.selectOne(goods_idx);

        // 기존 파일 가져오기
        // System.out.println(vo.getP_filename());
        File oldFile = new File(abs_path, vo.getImage_url());
        oldFile.delete();

        vo.setImage_url(image_url);
        goods_service.update_image_url(vo);

        //System.out.println(vo.getGoods_idx());
        // DB p_filename update

        map.put("image_url", image_url);

        return map;
    }

    /**
     * 상품 삭제
     */
    @RequestMapping("delete.do")
    public String goods_delete(HttpServletRequest request,int goods_idx, String image_url,
            @RequestParam(required = false) String[] imageFilenames) {


                MemberVo member = (MemberVo) request.getSession().getAttribute("user");

                // 로그아웃 상태
                if (member == null) {
                    return "redirect:../member/login_form.do?reason=session_timeout";
                    // session_timeout: 세션만료(로그아웃상태)
        
                }        
        String abs_path = application.getRealPath("/upload/");

        File oldFile = new File(abs_path, image_url);
        oldFile.delete();

        /** 해당 상품의 컨텐츠에 사용된 사진 파일 전부삭제 */
        if (imageFilenames != null) {
            abs_path = application.getRealPath("/resources/ckimage/");

            for (int i = 0; i < imageFilenames.length; i++) {
                oldFile = new File(abs_path, imageFilenames[i]);
                oldFile.delete();
            }
        }

        goods_service.goods_delete(goods_idx);
        return "redirect:list.do";
    }

}
