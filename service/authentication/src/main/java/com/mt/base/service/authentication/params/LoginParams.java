package com.mt.base.service.authentication.params;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class LoginParams {
    @NotEmpty(message = "用户名不能为空")
    @Size(min = 2, max = 12, message = "用户名应为2~12个字符")
    private String username;

    @NotEmpty(message = "密码不能为空")
    @Size(min = 6, max = 64, message = "密码应至少6个字符")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
