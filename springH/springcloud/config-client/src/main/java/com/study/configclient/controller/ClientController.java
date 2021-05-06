package com.study.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
    @Value("${word}")
    private String word;

    @RequestMapping("/hello")
    public String index(@RequestParam String name) {
        return name+","+ word;
    }
}
