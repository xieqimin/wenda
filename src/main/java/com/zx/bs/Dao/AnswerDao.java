package com.zx.bs.Dao;

import com.zx.bs.model.Answer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AnswerDao {
    @Select("select answer_id,question_id,answer_content,user_id 'user.user_id' from answer where answer_id=#{id}")
    Answer findAnswerById(@Param("id") Integer id);

    @Insert("insert into answer (question_id,user_id,answer_content) values(#{question_id},#{user.user_id},#{answer_content});")
    Integer insertAnswer(Answer answer);

    @Update("update answer set answer_content=#{answer_content} where answer_id=#{answer_id}")
    Integer updateAnswer(Answer answer);

    @Select("select answer.answer_id, answer.question_id,answer.answer_content,user.user_id 'user.user_id' , user.user_name 'user.user_name' from answer inner join user on answer.user_id=user.user_id where question_id=#{id} ORDER BY  answer.answer_id DESC")
    List<Answer> findAnswerByQuestionId(@Param("id") Integer id);
    //
    @Select("select * from answer where user_id=#{id}")
    List<Answer> findAnswerByUserId(@Param("id") Integer id);

    @Select("select * from question where user_id=#{id} and question_id={q_id}")
    List<Answer> findAnswerByUserAndQuestionId(@Param("id") Integer id, @Param("q_id") Integer q_id);

    @Delete("delete from answer where answer_id =#{id}")
    Integer removeAnswerById(@Param("id") Integer id);
}
