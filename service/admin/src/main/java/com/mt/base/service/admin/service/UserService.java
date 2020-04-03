package com.mt.base.service.admin.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mt.base.service.admin.entity.User;
import com.mt.base.service.admin.entity.UserWithRole;
import com.mt.base.service.admin.mapper.UserMapper;
import com.mt.base.service.admin.query.UserQueryParams;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    private UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public PageInfo<UserWithRole> selectUsers(Integer page, Integer pageSize, UserQueryParams user) {
        PageHelper.startPage(page, pageSize);
        List<UserWithRole> users = this.userMapper.selectUserWithRole(user);
        return new PageInfo<>(users);
    }
}
