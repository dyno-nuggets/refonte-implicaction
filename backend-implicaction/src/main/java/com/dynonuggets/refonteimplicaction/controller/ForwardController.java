package com.dynonuggets.refonteimplicaction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForwardController {

    @GetMapping(value = "/**/{[path:[^^.]*}")
    public String redirect() {
        // redirige vers la home page pour préserver la route en cas de rafraichissement de la page
        return "forward:/";
    }
}
