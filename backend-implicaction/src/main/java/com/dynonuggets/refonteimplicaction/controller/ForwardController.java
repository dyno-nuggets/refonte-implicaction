package com.dynonuggets.refonteimplicaction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardController {

    @RequestMapping(value = "/**/{[path:[^^.]*}")
    public String redirect() {
        // redirige vers la home page pour préserver la route en cas de rafraichissement de la page
        return "forward:/";
    }
}
