package com.mt.base.service.authentication.mapper;

import com.mt.base.service.authentication.entity.User;
import tk.mybatis.mapper.common.BaseMapper;


public interface AuthMapper extends BaseMapper<User> {
    boolean checkUserExists(String username);
    User findByUsername(String username);
    void deleteByUsername(String username);
}
