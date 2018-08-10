package com.haron.pro.haron.aop;

import com.alibaba.fastjson.JSON;
import com.haron.pro.common.annotation.LogOperationTag;
import com.haron.pro.dao.entity.OpLog;
import com.haron.pro.dao.mapper.OpLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;

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
        if (!logOperationTag.required()) {
            return pjp.proceed();
        }
        OpLog opLog = new OpLog();
        String className = pjp.getTarget().getClass().getName();
        Signature signature = pjp.getSignature();
        opLog.setApi(className + "." + signature.getName());
        if (attributes == null) {
            log.error("请求为空");
        } else {
            HttpServletRequest request = attributes.getRequest();
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
                }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
                }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                }
            opLog.setIpAddress(ip);
            log.info("请求Request = method->{},\npathinfo->{},\ncontextPath->{},\nrequestURI->{},\nservletContext->{}," +
                            "\nauthType->{},\ncookies->{},\nremoteUser->{},\nservletPath->{}," +
                            "\nserverName->{},\nparameterMap->{},\nremoteAddr->{},\nrequestURL->{}",
                    request.getMethod(),
                    request.getPathInfo(),
                    request.getContextPath(),
                    request.getRequestURI(),
                    request.getServletContext(),
                    request.getAuthType(),
                    request.getCookies(),
                    request.getRemoteUser(),
                    request.getServletPath(),
//                    request.getUserPrincipal().toString(),
                    request.getServerName(),
                    request.getParameterMap(),
                    request.getRemoteAddr(),
                    request.getRequestURL());
            log.info("*********"+request.toString());
        }
        Object[] args = pjp.getArgs();
        if (args == null || args.length == 0) {
            opLog.setParam("没有参数");
        } else {
            StringBuilder params = new StringBuilder();
            Object o = args[0];
            params.append(o.toString());
            PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(o.getClass());  //得到属性数组
            for (PropertyDescriptor targetPd : targetPds) {//通过循环对属性一一赋值
                if (targetPd.getName().equals("openId")) {
                    PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(o.getClass(), targetPd.getName());
                    if (sourcePd != null && sourcePd.getReadMethod() != null) {//源对象是否具有读方法（getter）
                        Method readMethod = sourcePd.getReadMethod();
                        Object value = readMethod.invoke(o);//获取属性值
                        opLog.setOpenId(value.toString());
                    }
                    break;
                }
            }
            opLog.setParam(params.toString());
        }
        Object o = pjp.proceed();
        opLog.setResult(o.toString());
        opLogMapper.insertSelective(opLog);
        return o;
    }

}
