package com.hjg.spring.config.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/20
 */
@RestControllerAdvice
public class MyControllerAdvice {

    /**
     * 全局的数组越界处理器。
     * @param exception
     * @param request
     */
    @ExceptionHandler({ArrayIndexOutOfBoundsException.class})
    public void arrayOutHandler(ArrayIndexOutOfBoundsException exception, HttpServletRequest request) {

    }
}
