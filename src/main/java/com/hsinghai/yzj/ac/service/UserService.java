package com.hsinghai.yzj.ac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hsinghai.yzj.ac.domain.User;

import java.util.List;


public interface UserService extends IService<User> {
    List<User> getUsers();
}
