package com.hsinghai.yzj.ac.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hsinghai.yzj.ac.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM USER")
    List<User> getUsers();
}
