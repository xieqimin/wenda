package com.zx.bs.service;

import com.zx.bs.Dao.AnswerDao;

import com.zx.bs.model.Answer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class AnswerService {
    @Autowired
    private AnswerDao answerDao;

    public Answer findAnswerById(Integer id){
        Answer answer=answerDao.findAnswerById(id);
        return answer;
    }
    @Transactional
    public Integer insertanswer(Answer answer){
        //id 自动生成
        Integer integer=answerDao.insertAnswer(answer);
        return integer;
    }

    public List<Answer> findAnswerByUserId(int id){
        List<Answer> answerList=answerDao.findAnswerByUserId(id);
        return answerList;
    }
    public List<Answer> findAnswerByQuestionId(int id){
        List<Answer> answerList=answerDao.findAnswerByQuestionId(id);
        return answerList;
    }
    public List<Answer> findAnswerByUserAndQuestionId(int s_id, int q_id){
        List<Answer> questionIdList=answerDao.findAnswerByUserAndQuestionId(s_id,q_id);
        return questionIdList;
    }
    public Integer removeAnswerById(Integer id){
        return answerDao.removeAnswerById(id);
    }
}
