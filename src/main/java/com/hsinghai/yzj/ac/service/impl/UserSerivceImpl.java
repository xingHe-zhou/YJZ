package com.hsinghai.yzj.ac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hsinghai.yzj.ac.mapper.UserMapper;
import com.hsinghai.yzj.ac.domain.User;
import com.hsinghai.yzj.ac.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserSerivceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> getUsers() {
        return userMapper.getUsers();
    }
}
