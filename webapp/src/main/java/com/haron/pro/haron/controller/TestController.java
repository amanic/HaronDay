package com.haron.pro.haron.controller;

import com.haron.pro.common.annotation.LogOperationTag;
import com.haron.pro.common.annotation.WxMapping;
import com.haron.pro.common.module.user.WxUser;
import com.haron.pro.haron.schedule.RemindDateSchedule;
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

    @Autowired
    RemindDateSchedule remindDateSchedule;



    @GetMapping("t1")
    public String t1(@RequestParam(value = "i") Integer i){
        return testService.test1(i);
    }

    @GetMapping("t2")
    public String t2(){
        return testService.test2();
    }

    @GetMapping("t3")
    public String t3(@RequestParam("openId") String openId){
        return testService.test3(openId);
    }

    @GetMapping("t4")
    @LogOperationTag(isEntity = false)
    public String t4(String openId){
        return testService.test4(openId);
    }


    @GetMapping("t5")
    @LogOperationTag(isEntity = false)
    public String t5(@RequestParam("openId") String openId,@RequestParam("value") String value){
        return remindDateSchedule.remindDate();
    }

    /**
     * 测试{@link org.springframework.web.method.support.HandlerMethodArgumentResolver}的supportsParameter
     * @param wxUser
     * @return openID
     */
    @GetMapping("t6")
    @WxMapping
    public String t6(WxUser wxUser){
        return wxUser.getOpenId();
    }
}
