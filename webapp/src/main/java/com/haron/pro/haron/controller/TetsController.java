package com.haron.pro.haron.controller;

import com.haron.pro.common.util.WxWebUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenhaitao on 2018/7/26.
 */
@RestController
@RequestMapping("wx/test")
public class TetsController {

    @GetMapping("t1")
    public String t1(){
        return "hello~";
    }


    @GetMapping("t2")
    public String t2(){
        return "token是："+WxWebUtils.getWxWebUserFromSession().getOpenId();
    }
}
