package com.mt.base.service.admin.service;

import com.mt.base.service.admin.entity.Role;
import com.mt.base.service.admin.entity.RoleTag;
import com.mt.base.service.admin.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleService {
    private RoleMapper roleMapper;

    public RoleService(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public List<RoleTag> selectAllRoles() {
        return this.roleMapper.selectRoleTags();
    }
}
