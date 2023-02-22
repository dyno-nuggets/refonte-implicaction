package com.dynonuggets.refonteimplicaction.core.rest.controller;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

@Controller
public class ForwardController extends BasicErrorController {

    public ForwardController(final ErrorAttributes errorAttributes) {
        super(errorAttributes, new ErrorProperties());
    }

    @Override
    @GetMapping(produces = TEXT_HTML_VALUE)
    public ModelAndView errorHtml(final HttpServletRequest request, final HttpServletResponse response) {
        final HttpStatus status = getStatus(request);
        if (NOT_FOUND.equals(status)) {
            return new ModelAndView("forward:/");
        } else {
            return super.errorHtml(request, response);
        }
    }
}
