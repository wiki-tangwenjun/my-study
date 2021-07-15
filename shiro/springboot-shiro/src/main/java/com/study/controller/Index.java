package com.study.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/study")
public class Index {
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("name", "hello world");
        return "index";
    }

    @GetMapping("/addUser")
    public String add() {
        return "user/addUser";
    }

    @GetMapping("/updateUser")
    public String update() {

        return "user/updateUser";
    }

    @GetMapping("/toLogin")
    public String toLogin() {

        return "login";
    }

    @RequestMapping("/login")
    public String login(String userName, String password, Model model) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        try {
            subject.login(token);
            return "index";
        } catch (UnknownAccountException e) {
            model.addAttribute("name", "用户名不存在");
            return "login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("name", "密码错误");
            return "login";
        }
    }

    @RequestMapping("unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }

}
