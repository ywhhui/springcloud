package com.szcgc.comm.web;

import com.szcgc.comm.util.SundryUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author liaohong
 * @create 2020/8/24 18:04
 */
@Component
public class IbcArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(IbcId.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String str = ((HttpServletRequest) webRequest.getNativeRequest()).getHeader(WebConstants.HEADER_UID);
        return SundryUtils.tryGetInt(str, 0);
        //return 1;
    }
}
