package com.study.controller;

import com.study.mapper.UserMapper;
import com.study.pojo.UserPojo;
import com.study.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/findByName")
    public UserPojo findById(@RequestParam(value = "name") String name){
        UserPojo user = userService.findName(name);

        return user;
    }

    @GetMapping("/findAll")
    public List<UserPojo> findAll(){
        List<UserPojo> users = userService.findAll();
        return users;
    }
}
