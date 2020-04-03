package com.mt.base.common.response;


import java.util.Map;

public class JsonResponse {
    private Integer code;
    private String message;
    private String description;
    private Object data;

    public JsonResponse(Integer code, String message, String description, Object data) {
        this.code = code;
        this.message = message;
        this.description = description;
        this.data = data;
    }

    public JsonResponse(Integer code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 成功响应
     * @param description 中文描述
     * @return 响应对象
     */
    public static JsonResponse success(String description) {
        return new JsonResponse(200, "ok", description);
    }

    /**
     * 成功响应
     * @param description 中文描述
     * @param data 响应数据
     * @return 响应对象
     */
    public static JsonResponse success(String description, Object data) {
        return new JsonResponse(200, "OK", description, data);
    }

    /**
     * 失败响应
     * @param description 失败描述
     * @return 响应对象
     */
    public static JsonResponse error(String description) {
        return new JsonResponse(500, "error", description);
    }

    /**
     * 正常响应
     * @param code 响应码(Code文件中对应)
     * @param message 描述简码
     * @param description 中文描述
     * @param data 数据对象
     * @return 响应对象
     */
    public static JsonResponse response(Integer code, String message, String description, Object data) {
        return new JsonResponse(code, message, description, data);
    }

    /**
     * 正常响应
     * @param code 响应码(Code文件中对应)
     * @param message 描述简码
     * @param description 中文描述
     * @return 响应对象
     */
    public static JsonResponse response(Integer code, String message, String description) {
        return new JsonResponse(code, message, description);
    }

    public static JsonResponse unauthorized() {
        return new JsonResponse(100000, "unauthorized", "用户无权限");
    }

    public static JsonResponse unauthenticated() {
        return new JsonResponse(100002, "unauthenticated", "用户未登录");
    }

    public static JsonResponse forbidden() {
        return new JsonResponse(100003, "access denied", "访问受限");
    }

    public static JsonResponse authenticateFailed() {
        return new JsonResponse(100004, "authenticate failed", "登录失败");
    }

    public static JsonResponse paramsError(Map<String, String> data) { return new JsonResponse(100005, "params error", "参数错误", data); }

    public static JsonResponse userExists() { return new JsonResponse(100006, "user exists", "账户已存在"); }
}
