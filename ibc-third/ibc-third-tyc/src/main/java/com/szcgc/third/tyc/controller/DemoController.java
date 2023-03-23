package com.szcgc.third.tyc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2022/9/14 15:47
 */
@RestController
public class DemoController {

    @GetMapping("debug/isok")
    public String isOk() {
        return "ThirdTycApplication is ok " + LocalDateTime.now();
    }

    @GetMapping("debug/{id}")
    public String testId(@PathVariable int id) {
        return "ThirdTycApplication TestId " + id;
    }
}
