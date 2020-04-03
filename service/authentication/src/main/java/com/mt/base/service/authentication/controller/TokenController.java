package com.mt.base.service.authentication.controller;

import com.mt.base.common.response.JsonResponse;
import com.mt.base.common.utils.ClientUtils;
import com.mt.base.service.authentication.annotation.AnonymousAccess;
import com.mt.base.service.authentication.config.properties.TokenConfigProperties;
import com.mt.base.service.authentication.service.TokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(value = "/token")
public class TokenController {
    private TokenService tokenService;
    private TokenConfigProperties properties;

    public TokenController(TokenService tokenService, TokenConfigProperties properties) {
        this.tokenService = tokenService;
        this.properties = properties;
    }

    @GetMapping(value = "/checkToken")
    @AnonymousAccess
    public JsonResponse checkToken(HttpServletRequest request, String token) {
        String ip = ClientUtils.getClientIp(request);
        if (!this.properties.getWhiteList().contains(ip)) {
            return JsonResponse.error("该IP不在白名单内");
        }
        return JsonResponse.success("获取成功", this.tokenService.checkToken(token));
    }
}
