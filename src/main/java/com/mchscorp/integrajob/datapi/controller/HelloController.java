package com.mchscorp.integrajob.datapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/ping")
    public String ping() {
        return "pong desde Docker 🐳 + Spring Boot 🚀";
    }
}
