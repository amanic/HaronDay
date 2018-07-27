package com.haron.pro.haron.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenhaitao on 2018/7/26.
 */
@RestController
@RequestMapping("test")
public class TetsController {

    @GetMapping("t1")
    public String t1(){
        return "hello~";
    }
}
