package com.movie.FilmMatch.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("comments")
public class CommentsVo {
    
    int 	cmt_idx;
	String 	cmt_content;
	String	cmt_ip;
	String	cmt_regdate;
	int		b_idx;
	int		mem_idx;
	String	mem_id;
	String	mem_name;
	
	String 	mem_mask_id;
	int 	no;


    //댓글 mask
	public String getmem_mask_id() {
		//mem_id중 앞쪽 앞만노출시킨다. /나머지는 *** 처리
		
        //return mem_id.substring(0,1) + "**";
		
		int len = mem_id.length();
		int mask = Math.round(len / 2);

		return mem_id.substring(0, mask) + "*".repeat(len - mask);
	}
	

	public void setmem_mask_id(String mem_mask_id) {
		this.mem_mask_id = mem_mask_id;
	}


	//Vo에 대한 메소드를 만들어준다.
	public CommentsVo() {
		
	}
	/**
	 * 
	 * @param cmt_content
	 * @param cmt_ip
	 * @param b_idx
	 * @param mem_idx
	 * @param mem_id
	 * @param mem_name
	 */
	public CommentsVo(String cmt_content, String cmt_ip, int b_idx, int mem_idx, String mem_id, String mem_name) {
		super();
		this.cmt_content = cmt_content;
		this.cmt_ip = cmt_ip;
		this.b_idx = b_idx;
		this.mem_idx = mem_idx;
		this.mem_id = mem_id;
		this.mem_name = mem_name;
	}

}
