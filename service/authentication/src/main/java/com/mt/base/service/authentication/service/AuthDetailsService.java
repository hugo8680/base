package com.mt.base.service.authentication.service;

import com.mt.base.service.authentication.entity.AuthUser;
import com.mt.base.service.authentication.entity.User;
import com.mt.base.service.authentication.mapper.AuthMapper;
import io.undertow.util.BadRequestException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.NotActiveException;


@Service
public class AuthDetailsService implements UserDetailsService {
    private AuthMapper authMapper;

    AuthDetailsService(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }

    @Override
    public AuthUser loadUserByUsername(String s) throws UsernameNotFoundException {
        final User user = this.authMapper.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("用户名不存在: %s", s));
        }
        if (!user.getStatus()) {
            throw new UsernameNotFoundException("账户未激活");
        }
        // 创建UserDetails用户
        return new AuthUser(
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                user.getGender(),
                user.getPassword(),
                user.getAvatar(),
                user.getEmail(),
                user.getPhone(),
                user.getStatus(),
                user.getCreateTime(),
                user.getUpdateTime(),
                user.getRoles()
        );
    }
}
