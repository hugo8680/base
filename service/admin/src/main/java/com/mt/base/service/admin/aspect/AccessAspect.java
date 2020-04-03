package com.mt.base.service.admin.aspect;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mt.base.common.utils.HttpClientUtils;
import com.mt.base.service.admin.annotation.CheckAccess;
import io.undertow.util.BadRequestException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.nio.file.AccessDeniedException;


@Aspect
@Component
public class AccessAspect {
    @Value("${auth.checkTokenUrl}")
    private String checkTokenUrl;
    @Value("${auth.tokenHeader}")
    private String tokenHeader;
    @Value("${auth.header}")
    private String header;

    @Pointcut("@annotation(com.mt.base.service.admin.annotation.CheckAccess)")
    private void pointcut() {}

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        CheckAccess checkAccess = method.getAnnotation(CheckAccess.class);
        String role = checkAccess.role();
        String token = resolveToken(this.header, this.tokenHeader);
        JSONObject ret = HttpClientUtils.get(this.checkTokenUrl + "?token=" + token);
        if (ret.getJSONObject("data") != null && ret.getInteger("code") == 200) {
            JSONObject data = ret.getJSONObject("data");
            if (data.getBoolean("result")) {
                JSONArray array = data.getJSONArray("authorities");
                for (Object i : array) {
                    JSONObject ii = (JSONObject) JSONObject.toJSON(i);
                    if (ii.getString("authority").equals(role)) {
                        return pjp.proceed();
                    }
                }
            }
        }
        throw new BadRequestException("没有权限");
    }

    private static String resolveToken(String header, String tokenHeader) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        assert servletRequestAttributes != null;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String bearerToken = request.getHeader(header);
        if (bearerToken != null && bearerToken.startsWith(tokenHeader)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
