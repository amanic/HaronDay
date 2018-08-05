package com.haron.pro.haron.schedule;

import com.haron.pro.common.util.DateUtil;
import com.haron.pro.dao.entity.DateRemind;
import com.haron.pro.dao.mapper.DateRemindMapper;
import com.haron.pro.service.api.DateRemindService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenhaitao on 2018/7/29.
 */
@Slf4j
@Component
public class RemindDateSchedule {

    @Autowired
    DateRemindMapper dateRemindMapper;

    @Autowired
    DateRemindService dateRemindService;

    @Scheduled(cron = "0 0/15 * * * ?")
    public String remindDate(){
        List<DateRemind> dateReminds = dateRemindMapper.selectToRemind(DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
        if(null==dateReminds||dateReminds.size()==0){
            log.info("当前没有用户需要提醒");
            return "当前没有用户需要提醒";
        }else {
            List<Integer> ids = new ArrayList<>(dateReminds.size());
            for (DateRemind e:dateReminds) {
                ids.add(e.getId());
                dateRemindService.remind(e);
            }
            dateRemindMapper.updateRemindByIds(ids);
            return "成功";
        }
    }
}
