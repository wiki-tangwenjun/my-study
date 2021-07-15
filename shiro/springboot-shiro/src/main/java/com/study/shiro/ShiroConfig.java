package com.study.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    // ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);

        // shiro内置过滤器
        /**
         * anon: 无需认证直接访问
         * authc: 必须认证才能访问
         * user: 必须拥有记住我功能才能用
         * perms: 拥有某个资源权限才能用
         * role: 拥有某个角色权限才能用
         */
        Map<String, String> filterMap = new LinkedHashMap<String, String>();
        filterMap.put("/study/index", "authc");

        filterMap.put("/study/addUser", "perms[user:add]");
        filterMap.put("/study/updateUser", "perms[user:update]");

        bean.setFilterChainDefinitionMap(filterMap);
        bean.setLoginUrl("/study/toLogin");

        bean.setUnauthorizedUrl("/study/unauthorized");
        return bean;
    }

    // DefaultWebSecurityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);

        return securityManager;
    }

    // realm
    @Bean(name = "userRealm")
    public UserRealm userRealm() {

        return new UserRealm();
    }

}
