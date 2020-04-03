package com.mt.base.service.admin.controller;


import com.mt.base.common.response.JsonResponse;
import com.mt.base.service.admin.annotation.CheckAccess;
import com.mt.base.service.admin.query.UserQueryParams;
import com.mt.base.service.admin.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/list")
    @CheckAccess(role = "admin")
    public JsonResponse users(@RequestParam(value = "current", defaultValue = "1") Integer page, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, UserQueryParams user) {
        return JsonResponse.success("获取成功", this.userService.selectUsers(page, pageSize, user));
    }
}
