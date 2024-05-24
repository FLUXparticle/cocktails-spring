package org.example.coktail.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class AppController {

    @GetMapping("/app")
    public String redirectApp() {
        return "forward:/app/index.html";
    }

}
