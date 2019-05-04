package com.zx.bs.Dao;

import com.zx.bs.model.Answer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AnswerDao {
    @Select("select answer_id,question_id,answer_content,user_name 'user.name' from answer where answer_id=#{id}")
    Answer findAnswerById(@Param("id") Integer id);

    @Insert("insert into answer (question_id,user_name,answer_content) values(#{question_id},#{user.name},#{answer_content});")
    Integer insertAnswer(Answer answer);

    @Update("update answer set answer_content=#{answer_content} where answer_id=#{answer_id}")
    Integer updateAnswer(Answer answer);

    @Select("select answer_id, question_id, answer_content,user_name 'user.name'  from answer  where question_id=#{id} ORDER BY  answer.answer_id DESC")
    List<Answer> findAnswerByQuestionId(@Param("id") Integer id);

    @Select("select * from answer where user_name=#{id}")
    List<Answer> findAnswerByUserId(@Param("id") String id);

    @Select("select * from question where user_name=#{id} and question_id={q_id}")
    List<Answer> findAnswerByUserAndQuestionId(@Param("id") Integer id, @Param("q_id") Integer q_id);

    @Delete("delete from answer where answer_id =#{id}")
    Integer removeAnswerById(@Param("id") Integer id);
}
