package com.zx.bs.service;


import com.zx.bs.Dao.QuestionDao;
import com.zx.bs.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionDao questionDao;

    public Question findQuestionById(int id){
        Question question=questionDao.findQuestionById(id);
        return question;
    }
    @Transactional
    public Integer insertQuestion(Question question){
        Integer integer=questionDao.insertQuestion(question);
        return integer;
    }

    public List<Question> findQuestionByUserId(int id){
        List<Question> questionList=questionDao.findQuestionByUserId(id);
        return questionList;
    }

    public Integer updateQuestion(Question question){
        Integer result=questionDao.updateQuestion(question);
        return result;
    }
    //问题 userid name

    public List<Question> findQuestion(){
        List<Question> questionList=questionDao.findQuestion();
        return questionList;
    }

    public Integer removeQuestionById(Integer  id){
        return questionDao.removeQuestionById(id);
    }
    public List<Question> queryQuestion(String word){
        String str="%"+word+"%";
        return questionDao.queryQuestion(str);
    }

}
