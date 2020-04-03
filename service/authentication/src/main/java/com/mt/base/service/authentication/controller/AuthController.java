package com.mt.base.service.authentication.controller;


import com.mt.base.common.response.JsonResponse;
import com.mt.base.common.utils.ValidUtil;
import com.mt.base.service.authentication.annotation.AnonymousAccess;
import com.mt.base.service.authentication.entity.AuthUser;
import com.mt.base.service.authentication.entity.User;
import com.mt.base.service.authentication.params.LoginParams;
import com.mt.base.service.authentication.params.RegisterParams;
import com.mt.base.service.authentication.service.AuthService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


@RestController
@RequestMapping(value = "/api")
public class AuthController {
    private AuthService authService;

    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @AnonymousAccess
    @PostMapping(value = "/login")
    public JsonResponse login(@RequestBody @Valid LoginParams params, BindingResult result) {
        if (ValidUtil.hasErrors(result)) {
            return JsonResponse.paramsError(ValidUtil.getErrors(result));
        }
        Map<String, Object> ret = this.authService.login(params.getUsername(), params.getPassword());
        if (ret != null) {
            return JsonResponse.success("登录成功", ret);
        } else {
            return JsonResponse.authenticateFailed();
        }
    }

    @GetMapping(value = "/user")
    public JsonResponse userInfo() {
        AuthUser user = this.authService.getCurrentUser();
        return JsonResponse.success("获取用户信息成功", user);
    }

    @AnonymousAccess
    @PostMapping(value = "/register")
    public JsonResponse register(@RequestBody @Valid RegisterParams params, BindingResult result) {
        if (ValidUtil.hasErrors(result)) {
            return JsonResponse.paramsError(ValidUtil.getErrors(result));
        }
        User user = this.authService.register(params.getUsername(), params.getPassword(), params.getEmail());
        if (user != null) {
            return JsonResponse.success("注册成功", user);
        } else {
            return JsonResponse.userExists();
        }
    }

}
