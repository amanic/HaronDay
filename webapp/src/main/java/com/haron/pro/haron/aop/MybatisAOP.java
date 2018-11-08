package com.haron.pro.haron.aop;

import com.haron.pro.common.annotation.JudgeColumn;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by martea on 2018/11/8.
 */
@Aspect
@Component
public class MybatisAOP {

    @Around(value = "@annotation(judgeColumn)")
    public Object doMybatis(ProceedingJoinPoint pjp, JudgeColumn judgeColumn) throws Throwable {
        System.out.println("嘟嘟嘟嘟。。。。。。。。。。。");
        return pjp.proceed();
    }
}
