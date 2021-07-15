package com.study.mapper;

import com.study.pojo.UserPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    UserPojo findByName(@Param("name") String name);

    List<UserPojo> findAll();
}
