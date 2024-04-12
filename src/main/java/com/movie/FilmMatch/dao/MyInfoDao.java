package com.movie.FilmMatch.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.movie.FilmMatch.vo.MyInfoVo;
@Mapper
public interface MyInfoDao {
	
	// 주소목록조회
	public List<MyInfoVo> selectList(int mem_idx);

	public MyInfoVo selectOneList(int mem_idx);
	
	public MyInfoVo selectOneAddr(String addr_idx);

	public int insert(MyInfoVo vo);

	public int update(MyInfoVo vo);

	public int delete(int addr_idx);
	
}
