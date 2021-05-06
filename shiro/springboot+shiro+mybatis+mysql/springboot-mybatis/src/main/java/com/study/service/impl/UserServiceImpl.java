package com.study.service.impl;

import com.study.mapper.UserMapper;
import com.study.pojo.UserPojo;
import com.study.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserPojo findName(String name) {
        return userMapper.findByName(name);
    }

    @Override
    public List<UserPojo> findAll() {

        return userMapper.findAll();
    }
}
