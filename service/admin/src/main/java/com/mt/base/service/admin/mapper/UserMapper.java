package com.mt.base.service.admin.mapper;

import com.mt.base.service.admin.entity.User;
import com.mt.base.service.admin.entity.UserWithRole;
import com.mt.base.service.admin.query.UserQueryParams;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<UserWithRole> selectUserWithRole(UserQueryParams userQueryParams);
}
