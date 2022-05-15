package com.project.EStore.exception.handler;

import com.project.EStore.exception.ProductNotFoundException;
import com.project.EStore.exception.ProductQueryCriteriaException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductQueryCriteriaException.class)
    public ModelAndView productQueryCriteriaHandler() {
        //TODO: add handling and view

        return new ModelAndView();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ModelAndView productNotFoundHandler() {

        return new ModelAndView();
    }
}
