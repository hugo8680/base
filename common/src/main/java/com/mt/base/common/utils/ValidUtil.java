package com.mt.base.common.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;


/**
 * 校验参数
 */
public class ValidUtil {
    /**
     * 判断是否发生错误
     * @param result BindingResult
     * @return true或false
     */
    public static boolean hasErrors(BindingResult result) {
        return result.hasErrors();
    }

    /**
     * 返回错误参数字典
     * @param result BindingResult
     * @return 错误参数字典
     */
    public static Map<String, String> getErrors(BindingResult result) {
        Map<String, String> errs = new HashMap<>();
        for (FieldError fe : result.getFieldErrors()) {
            errs.put(fe.getField(), fe.getDefaultMessage());
        }
        return errs;
    }
}
