package com.mt.base.service.authentication.service;

import com.mt.base.service.authentication.entity.AuthUser;
import com.mt.base.service.authentication.entity.User;
import com.mt.base.service.authentication.mapper.AuthMapper;
import com.mt.base.service.authentication.token.JwtTokenProvider;
import com.mt.base.service.authentication.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


@Service
@Transactional
public class AuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private AuthMapper authMapper;
    private PasswordEncoder passwordEncoder;
    private AuthDetailsService authDetailsService;
    private JwtTokenProvider tokenProvider;
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthService(AuthMapper authMapper, AuthDetailsService authDetailsService, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.authMapper = authMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authDetailsService = authDetailsService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    public Map<String, Object> login(String username, String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = this.authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = this.tokenProvider.createToken(authentication);
            final AuthUser authUser = (AuthUser) authentication.getPrincipal();
//            User user = this.authMapper.findByUsername(username);
            Map<String, Object> ret = new HashMap<>();
            ret.put("token", token);
            ret.put("userInfo", authUser);
            return ret;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public AuthUser getCurrentUser() {
        return this.authDetailsService.loadUserByUsername(SecurityUtils.getUsername());
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public User register(String username, String password, String email) {
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(this.passwordEncoder.encode(password));
            user.setEmail(email);
            this.authMapper.insertSelective(user);
            return user;
        } catch (DuplicateKeyException e) {

            return null;
        }
    }
}
