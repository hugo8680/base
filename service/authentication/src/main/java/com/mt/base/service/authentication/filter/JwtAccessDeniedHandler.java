package com.mt.base.service.authentication.filter;


import com.alibaba.fastjson.JSONObject;
import com.mt.base.common.response.JsonResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * 未授权访问异常
     * @param request 请求
     * @param response 响应
     * @param e 未授权访问
     * @throws IOException 异常
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        JSONObject res = (JSONObject) JSONObject.toJSON(JsonResponse.forbidden());
        response.getWriter().append(res.toJSONString());
    }
}
