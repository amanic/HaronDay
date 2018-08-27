package com.haron.pro.haron.controller;

import com.haron.pro.common.annotation.LogOperationTag;
import com.haron.pro.common.annotation.WxMapping;
import com.haron.pro.common.module.user.WxUser;
import com.haron.pro.haron.schedule.RemindDateSchedule;
import com.haron.pro.service.api.TestService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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


    public static void climbData(String[] args) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault(); // 创建httpclient实例
        HttpGet httpget = new HttpGet("http://www.cnblogs.com/"); // 创建httpget实例

        CloseableHttpResponse response = httpclient.execute(httpget); // 执行get请求
        HttpEntity entity = response.getEntity(); // 获取返回实体
        String content = EntityUtils.toString(entity, "utf-8");
        response.close(); // 关闭流和释放系统资源

        Document doc = Jsoup.parse(content); // 解析网页 得到文档对象
        Elements elements = doc.getElementsByTag("title"); // 获取tag是title的所有DOM元素
        Element element = elements.get(0); // 获取第1个元素
        String title = element.text(); // 返回元素的文本
        System.out.println("网页标题是：" + title);

        Element element2 = doc.getElementById("site_nav_top"); // 获取id=site_nav_top的DOM元素
        String navTop = element2.text(); // 返回元素的文本
        System.out.println("口号：" + navTop);

    }
}
