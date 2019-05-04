package com.zx.bs.controller;

import com.zx.bs.model.Answer;
import com.zx.bs.model.Question;
import com.zx.bs.model.User;
import com.zx.bs.service.AnswerService;
import com.zx.bs.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

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
        if(session.getAttribute("user_name")!=null){
            User user=new User();
            user.setName((String) session.getAttribute("user_name"));

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
    public ModelAndView findAnswerById(@PathVariable("id") Integer id,Map<String,Object> map,HttpSession session){
        //TODO
        if(session.getAttribute("user_name")!=null)
        {
            map.put("login", true);
            map.put("user_name",session.getAttribute("user_name"));

        }else {
            map.put("login", false);
        }
        Answer answer= answerService.findAnswerById(id);
        map.put("answer",answer);
        return new ModelAndView("answer",map);
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
    public List<Answer> findAnswerByStudentId(@PathVariable("id") String id){

        return answerService.findAnswerByUserId(id);
    }

    @RequestMapping(value="/remove/answer/{id}", method = {RequestMethod.GET})
    //TODO ???是否返回界面
    @ResponseBody
    public String removeAnswerById(@PathVariable("id") Integer id,HttpSession session){
        //TODO
        int result=-1;
        String userName =(String) session.getAttribute("user_name");
        if(userName!=null){
            Answer answer=answerService.findAnswerById(id);

            if(answer!=null&&answer.getUser().getName().equals(userName))
                result= answerService.removeAnswerById(id);
        }

        if(result!=1)
            return "失败";
        else
            return "成功";
    }

    @RequestMapping(value="/answer/change/{id}", method = {RequestMethod.GET})
    public ModelAndView changeAnswerById(@PathVariable("id") Integer id , HttpSession session, Map<String,Object> map){
        //TODO
        Answer answer= answerService.findAnswerById(id);
        map.put("answer",answer);
        if(session.getAttribute("user_name")!=null) {
            if(session.getAttribute("user_name").equals(answer.getUser().getName())){
                map.put("login", true);
                map.put("user_name",session.getAttribute("user_name"));

                return new ModelAndView("updateanswer",map);
            }else {
                return new ModelAndView("noahu",map);
            }

        }else {
            return new ModelAndView("nologin",map);
        }

    }

    @RequestMapping(value="/answer/update/{id}", method = {RequestMethod.POST})
    //TODO ???是否返回界面
    @ResponseBody
    public String updateAnswer(@PathVariable("id") Integer id ,String answer_content,HttpSession session){
        if(session.getAttribute("user_name")!=null) {
            Answer answer= answerService.findAnswerById(id);
            if(session.getAttribute("user_name").equals(answer.getUser().getName())){
                answer.setAnswer_content(answer_content);
                Integer result=answerService.updateAnswer(answer);
                return ""+result;
            }else {
                //不是自己的问题
                return "-2";
            }
        }else {
            //没有登陆
            return "-1";
        }

    }
}
