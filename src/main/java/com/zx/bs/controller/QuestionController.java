package com.zx.bs.controller;

import com.zx.bs.model.Answer;
import com.zx.bs.model.Question;
import com.zx.bs.model.User;
import com.zx.bs.service.AnswerService;
import com.zx.bs.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Controller
public class QuestionController {

    //分页查询 mybaties
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    @RequestMapping(value="/")
    public ModelAndView index(Map<String,Object> map, HttpSession session){
        //TODO
        List<Question> questionList=questionService.findQuestion();
        map.put("question",questionList);
        if(session.getAttribute("user_name")!=null)
        {
            map.put("login", true);

            map.put("user_name",session.getAttribute("user_name"));
        }else {
            map.put("login", false);
        }
        return new ModelAndView("index",map);
    }
    @RequestMapping(value="/query")
    public ModelAndView query(String word,Map<String,Object> map, HttpSession session){
        //TODO
        List<Question> questionList=questionService.queryQuestion(word);
        map.put("question",questionList);
        if(session.getAttribute("user_name")!=null||session.getAttribute("admin_id")!=null)
        {
            map.put("login", true);

            map.put("user_name",session.getAttribute("user_name"));
        }else {
            map.put("login", false);
        }
        return new ModelAndView("query",map);
    }
    @RequestMapping(value="/editquestion")
    public ModelAndView editquestion(Map<String,Object> map, HttpSession session){
        //TODO

        if(session.getAttribute("user_name")!=null)
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
        if(session.getAttribute("user_name")!=null){
            User user=new User();
            user.setName((String)session.getAttribute("user_name"));
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
    public ModelAndView findQuestionById(@PathVariable("id") Integer id ,HttpSession session, Map<String,Object> map){
        //TODO
        Question question= questionService.findQuestionById(id);
        List<Answer> answerList=answerService.findAnswerByQuestionId(id);
        map.put("question",question);
        map.put("answers",answerList);
        if(session.getAttribute("user_name")!=null)
        {

            map.put("login", true);
            map.put("user_name",session.getAttribute("user_name"));
        }else {
            map.put("login", false);
        }
        return new ModelAndView("question",map);
    }

    @RequestMapping(value="/question/change/{id}", method = {RequestMethod.GET})
    public ModelAndView changeQuestionById(@PathVariable("id") Integer id ,HttpSession session, Map<String,Object> map){
        //TODO
        Question question= questionService.findQuestionById(id);
        map.put("question",question);
        if(session.getAttribute("user_name")!=null) {
            if(session.getAttribute("user_name").equals(question.getUser().getName())){
                map.put("login", true);
                map.put("user_name",session.getAttribute("user_name"));
                return new ModelAndView("updatequestion",map);
            }else {
                return new ModelAndView("noahu",map);
            }

        }else {
            return new ModelAndView("nologin",map);
        }

    }

    @RequestMapping(value="/question/update/{id}", method = {RequestMethod.POST})
    //TODO ???是否返回界面
    @ResponseBody
    public String updateQuestion(@PathVariable("id") Integer id ,String question_content,HttpSession session){
        if(session.getAttribute("user_name")!=null) {
            Question question= questionService.findQuestionById(id);
            if(session.getAttribute("user_name").equals(question.getUser().getName())){
                question.setQuestion_content(question_content);
                Integer result=questionService.updateQuestion(question);
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

    @RequestMapping(value="/user/{id}", method = {RequestMethod.GET})
    //???连带问题 用户界面
    public ModelAndView findAnswerByUserId(@PathVariable("id") String id,Map<String,Object> map,HttpSession session){

        List<Question> questionList= questionService.findQuestionByUserId(id);
        List<Answer> answerList=answerService.findAnswerByUserId(id);
        if(session.getAttribute("user_name")!=null&&session.getAttribute("user_name").equals(id))
        {

            map.put("login", true);
            map.put("user_name",session.getAttribute("user_name"));
            map.put("answers",answerList);
            map.put("question",questionList);
            return new ModelAndView("user",map);
        }else {
            map.put("login", false);
            return new ModelAndView("noahu",map);
        }

    }

    @RequestMapping(value="/remove/question/{id}", method = {RequestMethod.GET})
    @ResponseBody
    public Integer removeQuestionById(@PathVariable("id") Integer id,HttpSession session){
        //TODO
        Integer aid=(Integer) session.getAttribute("user_name");
        if(aid==null){
            Question answer=questionService.findQuestionById(id);
            String uid=(String) session.getAttribute("user_name");
            if(answer!=null&&answer.getUser().getName().equals(uid))
                return questionService.removeQuestionById(id);
            else {
                return -1;
            }
        }
        else
            return questionService.removeQuestionById(id);
    }

}
