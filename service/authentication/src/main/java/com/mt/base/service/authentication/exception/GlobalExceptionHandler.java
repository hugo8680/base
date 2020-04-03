package com.mt.base.service.authentication.exception;

import javax.servlet.http.HttpServletRequest;

import com.mt.base.common.response.JsonResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理器
 *
 * @author hUGO
 */
@RestControllerAdvice
public class GlobalExceptionHandler
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 权限校验失败 如果请求为ajax返回json，普通请求跳转页面
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public JsonResponse handleAuthorizationException(HttpServletRequest request, ExpiredJwtException e)
    {
        return JsonResponse.unauthenticated();
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
    public JsonResponse handleException(HttpRequestMethodNotSupportedException e)
    {
        LOGGER.error(e.getMessage(), e);
        return JsonResponse.error("不支持' " + e.getMethod() + "'请求");
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public JsonResponse notFount(RuntimeException e)
    {
        LOGGER.error("运行时异常:", e);
        return JsonResponse.error("运行时异常:" + e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public JsonResponse handleException(Exception e)
    {
        LOGGER.error(e.getMessage(), e);
        return JsonResponse.error("服务器错误，请联系管理员");
    }
}
