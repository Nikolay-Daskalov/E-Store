package com.project.EStore.exception.handler;

import com.project.EStore.exception.ProductCriteriaException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ProductCriteriaException.class})
    public ModelAndView sentExceptionView() {
        //TODO: add handling and view
        return null;
    }
}
