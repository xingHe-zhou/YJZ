package com.hsinghai.yzj.ac.controller;

import com.hsinghai.yzj.ac.domain.User;
import com.hsinghai.yzj.ac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userSerivce;

    @GetMapping("/test")
    public List<User> getUsers(){

        return userSerivce.getUsers();
    }
}
