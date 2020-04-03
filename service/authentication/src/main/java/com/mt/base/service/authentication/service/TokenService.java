package com.mt.base.service.authentication.service;

import com.mt.base.service.authentication.token.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class TokenService {
    private JwtTokenProvider tokenProvider;

    public TokenService(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public Map<String, Object> checkToken(String token) {
        Boolean result = this.tokenProvider.validateToken(token);
        Authentication authentication = this.tokenProvider.getAuthentication(token);
        Map<String, Object> ret = new HashMap<>();
        ret.put("result", result);
        ret.put("authorities", authentication.getAuthorities());
        return ret;
    }
}
