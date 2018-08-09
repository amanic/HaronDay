package com.haron.pro.haron.aop;

import com.haron.pro.common.annotation.LogOperationTag;
import com.haron.pro.dao.entity.OpLog;
import com.haron.pro.dao.mapper.OpLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chenhaitao on 2018/8/9.
 */
@Aspect
@Component
@Slf4j
public class OperationLogAop {

    @Autowired
    OpLogMapper opLogMapper;

    @Around(value = "@annotation(logOperationTag)")
    public Object LogOperation(ProceedingJoinPoint pjp, LogOperationTag logOperationTag) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        OpLog opLog = new OpLog();
        if(!logOperationTag.required()){
            return pjp.proceed();
        }
        if(attributes==null){
            log.error("请求为空");
        }else {
            HttpServletRequest request = attributes.getRequest();
            String remoteAddr = request.getRemoteAddr();
            String forwarded = request.getHeader("X-Forwarded-For");
            String realIp = request.getHeader("X-Real-IP");
            String ip = "";
            if (realIp == null) {
                if (forwarded == null) {
                    ip = remoteAddr;
                } else {
                    ip = remoteAddr + "/" + forwarded.split(",")[0];
                }
            } else {
                if (realIp.equals(forwarded)) {
                    ip = realIp;
                } else {
                    if(forwarded != null){
                        forwarded = forwarded.split(",")[0];
                    }
                    ip = realIp + "/" + forwarded;
                }
            }
            opLog.setIpAddress(ip);
            log.info("请求Request = method->{},\npathinfo->{},\ncontextPath->{},\nrequestURI->{},\nrequestURL->{}",request.getMethod(),request.getPathInfo(),request.getContextPath(),request.getRequestURI(),request.getRequestURL());
        }
        Object[] args = pjp.getArgs();
        if(args==null||args.length==0){
            //TODO 没有参数
            opLog.setParam("没有参数");
        }else {
            StringBuilder params = new StringBuilder();
            for (Object o: args) {
                params.append(o.toString()+"***");
            }
            opLog.setParam(params.toString());
        }
        Object o = pjp.proceed();
        opLog.setResult(o.toString());
        opLogMapper.insertSelective(opLog);
        return o;
    }
}
