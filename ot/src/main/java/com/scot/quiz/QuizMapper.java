package com.scot.quiz;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface QuizMapper {

    @Select("SELECT * FROM quiz WHERE id = #{id}")
    Quiz getQuiz(@Param("id") Integer id);

}