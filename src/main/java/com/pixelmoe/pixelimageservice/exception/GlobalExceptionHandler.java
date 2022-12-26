package com.pixelmoe.pixelimageservice.exception;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public void exceptionHandler(Throwable throwable, HttpServletResponse response) throws IOException {
        log.error(ExceptionUtil.stacktraceToString(throwable));
//        response.sendRedirect("/error.png");
    }
}
