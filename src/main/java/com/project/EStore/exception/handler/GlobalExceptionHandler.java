package com.project.EStore.exception.handler;

import com.project.EStore.exception.ProductNotFoundException;
import com.project.EStore.exception.ProductQueryCriteriaException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductQueryCriteriaException.class)
    public ModelAndView productQueryCriteriaHandler() {
        return this.buildModelAndView("error", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ModelAndView productNotFoundHandler() {
        return this.buildModelAndView("error", HttpStatus.NOT_FOUND);
    }

    private ModelAndView buildModelAndView(String viewName, HttpStatus httpStatus) {
        ModelAndView response = new ModelAndView(viewName, httpStatus);
        response.addObject("statusInfo", httpStatus.value() + " " + httpStatus.getReasonPhrase());

        return response;
    }
}
