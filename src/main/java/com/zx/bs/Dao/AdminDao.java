package com.zx.bs.Dao;

import com.zx.bs.model.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminDao {
    @Select("select * from adminl where admin_id=#{id}")
    Admin findAdminById(@Param("id") Integer id);
}
