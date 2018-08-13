package com.haron.pro.haron.aop;

import com.haron.pro.common.annotation.LogOperationTag;
import com.haron.pro.dao.entity.OpLog;
import com.haron.pro.dao.mapper.OpLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenhaitao on 2018/8/9.
 */
@Aspect
@Component
@Slf4j
public class OperationLogAop {

    private ThreadLocal<String> openId = new ThreadLocal<>();

    private ThreadLocal<String> param = new ThreadLocal<>();

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

    @Autowired
    OpLogMapper opLogMapper;

    @Around(value = "@annotation(logOperationTag)")
    public Object LogOperation(ProceedingJoinPoint pjp, LogOperationTag logOperationTag) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (!logOperationTag.required()) {
            return pjp.proceed();
        }
        String className = pjp.getTarget().getClass().getName();
        Signature signature = pjp.getSignature();
        Object[] args = pjp.getArgs();
        String[] params =  getParamsName(pjp);
        if (args != null && args.length != 0){
            if(logOperationTag.isEntity()){
                Object o = args[0];
                param.set(o.toString());
                PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(o.getClass());  //得到属性数组
                for (PropertyDescriptor targetPd : targetPds) {//通过循环对属性一一赋值
                    if (targetPd.getName().equals("openId")) {
                        PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(o.getClass(), targetPd.getName());
                        if (sourcePd != null && sourcePd.getReadMethod() != null) {//源对象是否具有读方法（getter）
                            Method readMethod = sourcePd.getReadMethod();
                            openId.set(readMethod.invoke(o).toString());
                        }
                        break;
                    }
                }
            }else {
                /*Map<String, Object> nameAndArgs = getFieldsName(this.getClass(), className, signature.getName(), args);//获取被切参数名称及参数值
                int offset = 0;
                for (String s: nameAndArgs.keySet()) {
                    nameAndArgs.put(params[offset],nameAndArgs.remove(s));
                }*/
                int offset = 0;
                Map<String,Object> nameAndArgs = new HashMap<>();
                for (String s : params) {
                    nameAndArgs.put(s,args[offset++]);
                }
                openId.set(nameAndArgs.get("openId").toString());
                param.set(nameAndArgs.toString());
            }
        }
        log.info("接收到请求**********路径是：{}，openId是：{}，拦截参数是：{}。",className+"."+signature.getName(),openId.get(),param.get());
        Object o = pjp.proceed();
        if(logOperationTag.required()){
            OpLog opLog = new OpLog();
            opLog.setIpAddress(getIpAdrress(attributes.getRequest()));
            opLog.setResult(o.toString());
            opLog.setApi(className + "." + signature.getName());
            opLog.setParam(param.get());
            opLog.setOpenId(openId.get());
            opLogMapper.insertSelective(opLog);
        }
        return o;
    }


    private static String getIpAdrress(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if(index != -1){
                return XFor.substring(0,index);
            }else{
                return XFor;
            }
        }
        XFor = Xip;
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }

/*

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
*/

    //返回方法的参数名
    private static String[] getParamsName(JoinPoint joinPoint) throws ClassNotFoundException, NoSuchMethodException {
        String classType = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        Class<?>[] classes = new Class[args.length];
        for (int k = 0; k < args.length; k++) {
            if (!args[k].getClass().isPrimitive()) {
                //获取的是封装类型而不是基础类型
                String result = args[k].getClass().getName();
                Class s = map.get(result);
                classes[k] = s == null ? args[k].getClass() : s;
            }
        }
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        //获取指定的方法，第二个参数可以不传，但是为了防止有重载的现象，还是需要传入参数的类型
        Method method = Class.forName(classType).getMethod(methodName, classes);
        String[] parameterNames = pnd.getParameterNames(method);
        return parameterNames;
    }

}
