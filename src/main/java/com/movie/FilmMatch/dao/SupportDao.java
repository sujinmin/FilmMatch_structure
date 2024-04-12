package com.movie.FilmMatch.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.movie.FilmMatch.vo.SupportVo;

@Mapper
public interface SupportDao {

	// @Select("select * support")
	public List<SupportVo> selectList();// 문의글 목록 조회

	public SupportVo selectOne(int b_idx);

	public int insert(SupportVo vo);

	public int delete(int b_idx);

	public int update(SupportVo vo);

}
