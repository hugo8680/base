package com.mt.base.service.authentication.params;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class RegisterParams {
    @NotEmpty(message = "用户名不能为空")
    @Size(min = 2, max = 12, message = "用户名应为2~12个字符")
    private String username;
    @NotEmpty(message = "密码不能为空")
    @Size(min = 6, max = 64, message = "密码应至少6个字符")
    private String password;
    @NotEmpty(message = "邮箱不能为空")
    @Pattern(regexp = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$", message = "邮箱格式不正确")
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
