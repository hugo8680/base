package com.mt.base.service.authentication.filter;

import com.alibaba.fastjson.JSONObject;
import com.mt.base.common.response.JsonResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 无登录凭证异常处理
     * @param request 请求
     * @param response 响应
     * @param e 登录凭证异常
     * @throws IOException 异常
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        JSONObject res = (JSONObject) JSONObject.toJSON(JsonResponse.unauthenticated());
        response.getWriter().append(res.toJSONString());
    }
}
