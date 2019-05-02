package com.zx.bs.service;

import com.zx.bs.Dao.AdminDao;

import com.zx.bs.model.Admin;
import com.zx.bs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminDao adminDao;

    public Integer findAdminByIdAndPasswd(Integer id, String pwd) {
        Admin admin = adminDao.findAdminById(id);
        if (admin != null)
            if (admin.getPasswd().equals(pwd))
                return 1;
            else
                return -1;//密码错误
        else return -2;//没有此id
    }
}
