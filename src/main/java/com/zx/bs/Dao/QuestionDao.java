package com.zx.bs.Dao;

import com.zx.bs.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface QuestionDao {
    @Select("select question_id, question_content,user_name 'user.name'  from question where question_id=#{id}")
    Question findQuestionById(@Param("id") Integer id);

    @Insert("insert into question (question_id,user_name,question_content) values(#{question_id},#{user.name},#{question_content});")
    Integer insertQuestion(Question question);

    @Select("select * from question where user_name=#{id}")
    List<Question> findQuestionByUserId(@Param("id") String id);

    @Select("select question_id, question_content,user_name 'user.name'  from question ORDER BY  question_id DESC")
    List<Question> findQuestion();

    @Update("update question set question_content=#{question_content} where question_id=#{question_id}")
    Integer updateQuestion(Question question);

    @Delete("delete from question where question_id =#{id}")
    Integer removeQuestionById(@Param("id") Integer  id);


    @Select("select question_id, question_content,user_name 'user.name'  from question  where question_content like  #{word} ;")
    List<Question> queryQuestion(@Param("word") String ser);
}
