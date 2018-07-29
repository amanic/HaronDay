package com.haron.pro.haron.controller;

import com.haron.pro.service.api.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by chenhaitao on 2018/7/26.
 */
@RestController
@RequestMapping("wx/test")
public class TestController {

    @Autowired
    TestService testService;



    @GetMapping("t1")
    public String t1(@RequestParam(value = "i") Integer i){
        return testService.test1(i);
    }

    @GetMapping("t2")
    public String t2(){
        return testService.test2();
    }

    @GetMapping("t3")
    public String t3(){
        return testService.test3();
    }
}
