package com.example.naumen.http.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = "com.example.naumen.http.controller")
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
}
