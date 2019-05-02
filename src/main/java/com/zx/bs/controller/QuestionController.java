package com.zx.bs.controller;

import com.zx.bs.model.Answer;
import com.zx.bs.model.Question;
import com.zx.bs.model.User;
import com.zx.bs.service.AnswerService;
import com.zx.bs.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

//TODO 编写问题

@Controller
public class QuestionController {

    //分页查询 mybaties
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    @RequestMapping(value="/")
    //TODO ???是否返回界面 是否返回回答列表
    public ModelAndView index(Map<String,Object> map, HttpSession session){
        //TODO
        List<Question> questionList=questionService.findQuestion();
        map.put("question",questionList);
        if(session.getAttribute("user_id")!=null||session.getAttribute("admin_id")!=null)
        {
            map.put("login", true);
            map.put("user_name",session.getAttribute("user_name"));
        }else {
            map.put("login", false);
        }
        return new ModelAndView("index",map);
    }
    @RequestMapping(value="/query")
    //TODO ???是否返回界面 是否返回回答列表
    public ModelAndView query(String word,Map<String,Object> map, HttpSession session){
        //TODO
        List<Question> questionList=questionService.queryQuestion(word);
        map.put("question",questionList);
        if(session.getAttribute("user_id")!=null||session.getAttribute("admin_id")!=null)
        {
            map.put("login", true);
            map.put("user_name",session.getAttribute("user_name"));
        }else {
            map.put("login", false);
        }
        return new ModelAndView("query",map);
    }
    @RequestMapping(value="/editquestion")
    //TODO ???是否返回界面 是否返回回答列表
    public ModelAndView editquestion(Map<String,Object> map, HttpSession session){
        //TODO

        if(session.getAttribute("user_id")!=null)
        {
            map.put("login", true);
            map.put("user_name",session.getAttribute("user_name"));
        }else {
            map.put("login", false);
        }
        return new ModelAndView("editquestion",map);
    }

    //添加问题
    @RequestMapping(value="/questionAdd", method = {RequestMethod.POST})
    @ResponseBody
    public Integer addQusetion(Question question , HttpSession session){
        //TODO userid
        Integer result;
        if(session.getAttribute("user_id")!=null){
            User user=new User();
            user.setUser_id((String)session.getAttribute("user_id"));
            question.setUser(user);
            result=questionService.insertQuestion(question);
        }
        else {
            result=-1;
        }
        return result;
    }

    //通过问题id查询问题
    @RequestMapping(value="/question/{id}", method = {RequestMethod.GET})
    //TODO ???是否返回界面 是否返回回答列表 是 是
    public ModelAndView findQuestionById(@PathVariable("id") Integer id ,HttpSession session, Map<String,Object> map){
        //TODO
        Question question= questionService.findQuestionById(id);
        List<Answer> answerList=answerService.findAnswerByQuestionId(id);
        map.put("question",question);
        map.put("answers",answerList);
        if(session.getAttribute("user_id")!=null||session.getAttribute("admin_id")!=null)
        {
            map.put("login", true);
            map.put("user_name",session.getAttribute("user_name"));
        }else {
            map.put("login", false);
        }
        return new ModelAndView("question",map);
    }

    @RequestMapping(value="/updatequestion/{id}", method = {RequestMethod.GET})
    //TODO ???是否返回界面
    @ResponseBody
    public String updateQuestion(Question question){
        Integer result=questionService.updateQuestion(question);
        return ""+result;
    }

    @RequestMapping(value="/user/{id}", method = {RequestMethod.GET})
    //???连带问题 用户界面
    @ResponseBody
    public List<Answer> findAnswerByUserId(@PathVariable("id") Integer id){
        List<Question> questionList= questionService.findQuestionByUserId(id);
        List<Answer> answerList=answerService.findAnswerByUserId(id);
        return answerList;
    }

    @RequestMapping(value="/remove/question/{id}", method = {RequestMethod.GET})
    //TODO ???是否返回界面
    @ResponseBody
    public Integer removeQuestionById(@PathVariable("id") Integer id,HttpSession session){
        //TODO
        Integer aid=(Integer) session.getAttribute("admin_id");
        if(aid==null){
            Question answer=questionService.findQuestionById(id);
            String uid=(String) session.getAttribute("user_id");
            if(answer!=null&&answer.getUser().getUser_id().equals(uid))
                return questionService.removeQuestionById(id);
            else {
                return -1;
            }
        }
        else
            return questionService.removeQuestionById(id);
    }

}
