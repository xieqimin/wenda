package com.zx.bs.Dao;

import com.zx.bs.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {
    @Select("select * from user where name=#{id}")
    User findUserById(@Param("id") String id);

    @Select("select * from user where name=#{id},password=#{pwd}")
    User findUserByIdAndPasswd(@Param("id") String id, @Param("pwd") String pwd);

    @Insert("INSERT  INTO user  VALUES(#{name},#{password});")
    Integer InsertUser(User user);

    @Delete("delete from user where name =#{id}")
    Integer DeleteUserById(@Param("id") String id);
}
