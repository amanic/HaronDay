package com.haron.pro.haron.controller;

import com.haron.pro.service.api.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by martea on 2018/11/2.
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    TestService testService;

    @RequestMapping("test")
    @ResponseBody
    public String test(Integer i){
        testService.test6(i);
        return "success!";
    }

}
