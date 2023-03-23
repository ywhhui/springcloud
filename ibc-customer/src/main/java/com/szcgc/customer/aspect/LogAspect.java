package com.szcgc.customer.aspect;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.constant.Const;
import com.szcgc.comm.exception.BaseException;
import com.szcgc.comm.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut(value = "execution(* com.szcgc.customer.controller..*.*(..))")
    public void pointCut() {
    }

    @Pointcut(value = "execution(* com.szcgc.customer.feign..*.*(..))")
    public void pointCut2() {
    }

    @Around("pointCut2()")
    public Object around2(ProceedingJoinPoint point) {
        return around(point);
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) {
        Object res = null;
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        String className = point.getTarget().getClass().getName();
        String methodName = method.getName();
        String finishMethodName = className.concat(".").concat(methodName);
        long start = System.currentTimeMillis();
        try {
            String[] paramNames = signature.getParameterNames();
            Object[] paramValus = point.getArgs();

            String paramStr = Const.Symbol.DOT;

            for (int i = 0, len = paramNames.length; i < len; i++) {
                if (paramValus[i] instanceof MultipartFile || paramValus[i] instanceof HttpServletResponse || ObjectUtil.isNull(paramValus[i])) {
                    continue;
                }
                paramStr = paramStr.concat(paramNames[i]).concat(Const.Symbol.EQUALS).concat(JsonUtils.toJSONString(paramValus[i])).concat(Const.Symbol.DOT);
            }
            paramStr = paramStr.substring(1);

            if (StrUtil.isNotBlank(paramStr)) {
                log.info("调用{}开始,参数:{}", finishMethodName, paramStr);
            } else {
                log.info("调用{}开始...", finishMethodName);
            }

            res = point.proceed();
            if (res instanceof IbcResponse) {
                IbcResponse response = (IbcResponse) res;
                if (response.data instanceof List && CollectionUtil.isNotEmpty((List) response.data)) {
                    log.info("调用{}结果集合长度为:{}", finishMethodName, ((List) response.data).size());
                    return res;
                }
            } else if (res instanceof HttpServletResponse) {
                return res;
            }

            log.info("调用{}结果为:{}", finishMethodName, JsonUtils.toJSONString(res));
        } catch (BaseException e) {
            log.error("调用{}异常", finishMethodName, e);
            return IbcResponse.error500(e.getMessage());
        } catch (Throwable e) {
            log.error("调用{}异常", finishMethodName, e);
            return IbcResponse.error500("程序异常请联系管理员");
        } finally {
            log.info("调用{}完毕,共耗时[{}]ms", finishMethodName, System.currentTimeMillis() - start);
        }
        return res;
    }


}
