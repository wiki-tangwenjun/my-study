package com.study.service;

import com.study.pojo.UserPojo;

import java.util.List;

public interface UserService {

    UserPojo findName(String name);

    List<UserPojo> findAll();
}
