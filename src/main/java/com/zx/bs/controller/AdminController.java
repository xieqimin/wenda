package com.zx.bs.controller;

import com.zx.bs.model.Admin;

import com.zx.bs.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(value="/AdminLogin", method = {RequestMethod.POST})
    @ResponseBody
    public Integer login(Admin user, HttpSession session){
        int id=user.getAdmin_id();
        String pwd=user.getPasswd();
        //System.out.println(id);
        //System.out.println(pwd)
        Integer result= adminService.findAdminByIdAndPasswd(id,pwd);
        if(result==1) {
            session.setAttribute("admin_id", id);
            session.setAttribute("user_name","管理员");
            //return "redirect:/";
//            map.put("user_name",user1.getUser_name());
//            map.put("question",questionService.findQuestion());
//            map.put("login",true);
        }
        return result;
    }
}
