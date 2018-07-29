package com.haron.pro.haron.schedule;

import com.haron.pro.common.util.DateUtil;
import com.haron.pro.dao.entity.DateRemind;
import com.haron.pro.dao.mapper.DateRemindMapper;
import com.haron.pro.service.api.DateRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by chenhaitao on 2018/7/29.
 */
@Component
public class RemindDateSchedule {

    @Autowired
    DateRemindMapper dateRemindMapper;

    @Autowired
    DateRemindService dateRemindService;

    @Scheduled(cron = "")
    public String remindDate(){
        List<DateRemind> dateReminds = dateRemindMapper.selectToRemind(DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
        if(null==dateReminds||dateReminds.size()==0){
            return "当前没有用户需要提醒";
        }else {
            for (DateRemind e:dateReminds) {
                dateRemindService.remind(e);
            }
            return "成功";
        }
    }
}
