package com.movie.FilmMatch.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

/**
 * support에 대한 데이터 객체
 */
@Data
@Alias("support")
public class SupportVo {

    int b_idx; // 문의 게시판 식별을 위한 고유번호
    String b_subject; // "" 게시판 제목
    String b_content; // "" 게시판 내용
    String b_ip; // "" 게시판 사용자 아이피
    String b_regdate; // "" 게시판 작성일자
    int b_readhit; // "" 게시판 조회수
    int mem_idx; // "" 게시판 회원번호
    String mem_name; // "" 게시판 회원명
    int b_ref; // "" 게시판 참조글번호
    int b_step; // "" 게시판 글순서번호
    int b_depth; // "" 게시판 글의 깊이
    String b_use; // "" 게시판 사용유무
    int no; // "" 게시판 rank() : 글번호컬럼명
    int cmt_count; // "" 게시판 댓글의 개수

    int community_page; // "" 게시판 페이지

    public SupportVo() {
    }

    /**
     * 문의하기(새글쓰기)에 대한 포장
     * 
     * @param b_idx
     * @param b_subject
     * @param b_content
     * @param b_ip
     * @param mem_idx
     * @param mem_name
     * @param b_ref
     * @param community_page
     */
    public SupportVo(int b_idx, String b_subject, String b_content, String b_ip, int mem_idx, String mem_name,
            int b_ref, int community_page) {
        this.b_idx = b_idx;
        this.b_subject = b_subject;
        this.b_content = b_content;
        this.b_ip = b_ip;
        this.mem_idx = mem_idx;
        this.mem_name = mem_name;
        this.b_ref = b_ref;
        this.community_page = community_page;
    }

    /**
     * 답글쓰기(communuty_page)의 포장
     * 
     * @param b_idx
     * @param b_subject
     * @param b_content
     * @param b_ip
     * @param mem_idx
     * @param mem_name
     * @param b_ref
     * @param b_step
     * @param b_depth
     * @param community_page
     */
    public SupportVo(int b_idx, String b_subject, String b_content, String b_ip, int mem_idx, String mem_name,
            int b_ref, int b_step, int b_depth, int community_page) {
        this.b_idx = b_idx;
        this.b_subject = b_subject;
        this.b_content = b_content;
        this.b_ip = b_ip;
        this.mem_idx = mem_idx;
        this.mem_name = mem_name;
        this.b_ref = b_ref;
        this.b_step = b_step;
        this.b_depth = b_depth;
        this.community_page = community_page;
    }

    /**
     * support_qna_modifyform 수정폼
     * 
     * @param b_idx
     * @param b_subject
     * @param b_content
     * @param b_ip
     */
    public SupportVo(int b_idx, String b_subject, String b_content, String b_ip) {
        this.b_idx = b_idx;
        this.b_subject = b_subject;
        this.b_content = b_content;
        this.b_ip = b_ip;
    }

}