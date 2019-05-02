package com.zx.bs.controller;

import com.zx.bs.model.Answer;
import com.zx.bs.model.User;
import com.zx.bs.service.AnswerService;
import com.zx.bs.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

//TODO 编写回答
@Slf4j
@Controller
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;


    @RequestMapping(value="/answerAdd", method = {RequestMethod.POST})
    @ResponseBody
    public Integer addAnswer (Answer answer ,HttpSession session){

        log.info(""+answer);
        //TODO 前端测试
        Integer result;
        if(session.getAttribute("user_id")!=null){
            User user=new User();
            user.setUser_id((String) session.getAttribute("user_id"));

            answer.setUser(user);

            result=answerService.insertanswer(answer);
        }
        else {
            // TODO return 登陆 ? 登陆后跳转到原来的url?
            result=-1;
        }
        return result;
    }

    //通过回答id查询回答
    @RequestMapping(value="/answer/{id}", method = {RequestMethod.GET})
    //TODO ???是否返回界面
    @ResponseBody
    public Answer findAnswerById(@PathVariable("id") Integer id){
        //TODO
        return answerService.findAnswerById(id);
    }

    //通过问题id查询回答，返回该问题的所有回答的列表
    @RequestMapping(value="/answerFind/{id}", method = {RequestMethod.GET})
    //???
    @ResponseBody
    public List<Answer> findAnswerByQuestionId(@PathVariable("id") Integer id){
        return answerService.findAnswerByQuestionId(id);
    }

    //通过用户id查询回答，返回该学生的所有回答的列表
    @RequestMapping(value="/answerFind/User/{id}", method = {RequestMethod.GET})
    //???连带问题 用户界面
    @ResponseBody
    public List<Answer> findAnswerByStudentId(@PathVariable("id") Integer id){

        return answerService.findAnswerByUserId(id);
    }

    @RequestMapping(value="/remove/answer/{id}", method = {RequestMethod.GET})
    //TODO ???是否返回界面
    @ResponseBody
    public String removeAnswerById(@PathVariable("id") Integer id,HttpSession session){
        //TODO
        int result=-1;
        Integer aid=(Integer) session.getAttribute("admin_id");
        if(aid==null){
            Answer answer=answerService.findAnswerById(id);
            String uid=(String) session.getAttribute("user_id");
            if(answer!=null&&answer.getUser().getUser_id().equals(uid))
                result= answerService.removeAnswerById(id);
        }
        else {
            result = answerService.removeAnswerById(id);
        }
        if(result!=1)
            return "失败";
        else return "成功";
    }
}
