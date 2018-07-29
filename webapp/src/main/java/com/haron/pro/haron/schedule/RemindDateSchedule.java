package com.haron.pro.haron.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by chenhaitao on 2018/7/29.
 */
@Component
public class RemindDateSchedule {


    @Scheduled(cron = "")
    public void remindDate(){

    }
}
