package com.project.EStore.exception.handler;

import com.project.EStore.exception.CartCookieException;
import com.project.EStore.exception.ProductNotFoundException;
import com.project.EStore.exception.ProductCriteriaException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ProductCriteriaException.class, CartCookieException.class})
    public ModelAndView productQueryCriteriaHandler() {
        return new ModelAndView("error", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ModelAndView productNotFoundHandler() {
        return new ModelAndView("error", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ModelAndView httpMethodNotAllowedHandler() {
        return new ModelAndView("error", HttpStatus.METHOD_NOT_ALLOWED);
    }
}