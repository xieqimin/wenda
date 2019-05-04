package com.zx.bs.service;


import com.zx.bs.Dao.UserDao;
import com.zx.bs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public Integer findUserByIdAndPasswd(String id, String pwd) {
        User user = userDao.findUserById(id);
        if (user != null)
            if (user.getPassword().equals(pwd))
                return 1;
            else
                return -1;//密码错误
        else return -2;//没有此id
    }
    @Transactional
    public Integer insertUser(User student){
        Integer integer= userDao.InsertUser(student);

        return integer;
    }

    public Integer deleteUserById(String id){
        Integer integer= userDao.DeleteUserById(id);
        return integer;
    }
    public User findUserById(String id){
        User student= userDao.findUserById(id);
        return student;
    }
}
