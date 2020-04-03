package com.mt.base.service.admin.mapper;

import com.mt.base.service.admin.entity.Role;
import com.mt.base.service.admin.entity.RoleTag;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    List<RoleTag> selectRoleTags();
}
