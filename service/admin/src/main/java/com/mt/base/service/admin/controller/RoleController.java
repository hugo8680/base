package com.mt.base.service.admin.controller;

import com.mt.base.common.response.JsonResponse;
import com.mt.base.service.admin.annotation.CheckAccess;
import com.mt.base.service.admin.service.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/role")
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(value = "/tags")
    @CheckAccess(role = "admin")
    public JsonResponse tags() {
        return JsonResponse.success("获取成功", this.roleService.selectAllRoles());
    }
}
