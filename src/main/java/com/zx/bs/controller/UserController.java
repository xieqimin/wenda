package com.zx.bs.controller;

import com.zx.bs.model.User;
import com.zx.bs.service.QuestionService;
import com.zx.bs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

//TODO 登陆id addquestiontianjiauserid
//TODO html模板更改
//TODO 路径
//TODO 问题和回答的修改 用户界面 回答界面？
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;

    @RequestMapping(value="/login", method = {RequestMethod.POST})
    @ResponseBody
    public Integer login(User user, HttpSession session){
        String id=user.getUser_id();
        String pwd=user.getUser_passwd();
        //System.out.println(id);
        //System.out.println(pwd)
        Integer result= userService.findUserByIdAndPasswd(id,pwd);
        if(result==1) {
            session.setAttribute("user_id", id);
            //return "redirect:/";
            User user1=userService.findUserById(id);
            session.setAttribute("user_name",user1.getUser_name());
//            map.put("user_name",user1.getUser_name());
//            map.put("question",questionService.findQuestion());
//            map.put("login",true);
        }
        return result;
    }
    //TODO
    //
    @RequestMapping(value="/registered", method = {RequestMethod.POST})
    @ResponseBody
    public Integer addUser(User user, HttpSession session){
        Integer result= userService.insertUser(user);
        if(result==1) {
            session.setAttribute("user_id", user.getUser_id());
            //return "redirect:/";
            session.setAttribute("user_name",user.getUser_name());
//            map.put("user_name",user1.getUser_name());
//            map.put("question",questionService.findQuestion());
//            map.put("login",true);
        }
        return result;
    }

    //推出登陆
    @RequestMapping(value="/out")
    public String UserOut(HttpSession session){
        session.removeAttribute("user_id");
        session.removeAttribute("user_name");
        session.removeAttribute("admin_id");
        //TODO
        return "redirect:/login.html";
    }
}
