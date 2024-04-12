package com.movie.FilmMatch.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.movie.FilmMatch.vo.CategoriesVo;

@Mapper
public interface CategoriesDao {

    /*
     * 카테고리 조회
     */
    public List<CategoriesVo> selectList(Map<String,Object> map);

    /**
     * 서브카테고리의 값을 통해 대분류 코드값을 구하기
     * @param cate_sub_id
     * @return
     */
    public int isSelected_sub_cate_parent_id(int cate_sub_id);

}
