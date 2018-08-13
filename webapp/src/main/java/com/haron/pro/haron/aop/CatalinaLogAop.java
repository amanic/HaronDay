package com.haron.pro.haron.aop;

import com.haron.pro.common.annotation.LogCatalina;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenhaitao on 2018/8/13.
 */
@Aspect
@Component
@Slf4j
public class CatalinaLogAop {


    private static HashMap<String, Class> map = new HashMap<String, Class>() {
        {
            put("java.lang.Integer", int.class);
            put("java.lang.Double", double.class);
            put("java.lang.Float", float.class);
            put("java.lang.Long", long.class);
            put("java.lang.Short", short.class);
            put("java.lang.Boolean", boolean.class);
            put("java.lang.Char", char.class);
        }
    };

    @Around(value = "@annotation(logCatalina)")
    public Object catalinaLog(ProceedingJoinPoint joinPoint, LogCatalina logCatalina) throws Throwable {
        String openId = "";
        String param = "";
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        String classType = joinPoint.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        String methodName = joinPoint.getSignature().getName(); //获取方法名称
        Object[] args = joinPoint.getArgs();//参数
        if(logCatalina.isEntity()){
            Object o = args[0];
            param = o.toString();
            PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(o.getClass());  //得到属性数组
            for (PropertyDescriptor targetPd : targetPds) {//通过循环对属性一一赋值
                if (targetPd.getName().equals("openId")) {
                    PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(o.getClass(), targetPd.getName());
                    if (sourcePd != null && sourcePd.getReadMethod() != null) {//源对象是否具有读方法（getter）
                        Method readMethod = sourcePd.getReadMethod();
                        Object value = readMethod.invoke(o);//获取属性值
                        openId = value.toString();
                    }
                    break;
                }
            }
        }else {
            Map<String, Object> nameAndArgs = getFieldsName(this.getClass(), clazzName, methodName, args);//获取被切参数名称及参数值
            openId = nameAndArgs.get("openId").toString();
            param = nameAndArgs.toString();
        }
        log.info("接收到请求**********路径是：{}，openId是：{}，拦截参数是：{}。",clazzName+"."+methodName,openId,param);
        return joinPoint.proceed();
    }


    private Map<String, Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws NotFoundException {
        Map<String, Object> map = new HashMap<String, Object>();

        ClassPool pool = ClassPool.getDefault();
        //ClassClassPath classPath = new ClassClassPath(this.getClass());
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            // exception
        }
        // String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++) {
            map.put(attr.variableName(i + pos), args[i]);//paramNames即参数名
        }
        return map;
    }
}
