package com.zx.bs.Dao;

import com.zx.bs.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface QuestionDao {
    @Select("select question.question_id, question.question_conent,user.user_id 'user.user_id' ,user.user_name 'user.user_name' from question inner join user  on question.user_id=user.user_id  where question_id=#{id}")
    Question findQuestionById(@Param("id") Integer id);

    @Insert("insert into question (question_id,user_id,question_conent) values(#{question_id},#{user.user_id},#{question_conent});")
    Integer insertQuestion(Question question);

    @Select("select * from question where user_id=#{id}")
    List<Question> findQuestionByUserId(@Param("id") Integer id);

    @Select("select question.question_id, question.question_conent,user.user_id 'user.user_id' ,user.user_name 'user.user_name' from question inner join user  on question.user_id=user.user_id ORDER BY  question.question_id DESC")
    List<Question> findQuestion();

    @Update("update question set question_conent=#{question_conent} where question_id=#{question_id}")
    Integer updateQuestion(Question question);

    @Delete("delete from question where question_id =#{id}")
    Integer removeQuestionById(@Param("id") Integer  id);

    //TODO
    @Select("select question.question_id, question.question_conent,user.user_id 'user.user_id' ,user.user_name 'user.user_name' from question inner join user  on question.user_id = user.user_id where question_conent like  #{word} ;")
    List<Question> queryQuestion(@Param("word") String ser);
}
