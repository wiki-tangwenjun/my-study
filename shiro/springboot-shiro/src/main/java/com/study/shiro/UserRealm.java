package com.study.shiro;

import com.study.pojo.UserPojo;
import com.study.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;

public class UserRealm extends AuthorizingRealm {
    @Resource
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("doGetAuthorizationInfo=>> 授权");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermission("user:add");

        Subject subject = SecurityUtils.getSubject();
        UserPojo userPromise = (UserPojo) subject.getPrincipal();
        simpleAuthorizationInfo.addStringPermission(userPromise.getPromise());

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("doGetAuthenticationInfo=> 认证");
        // 获取当前用户

        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        UserPojo user = userService.findName(userToken.getUsername());
        if (user == null) {
            return null;
        }

        return new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }
}
