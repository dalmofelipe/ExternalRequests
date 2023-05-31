package com.dalmofelipe.ExternalsRequests.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/web-client")
public class WebClientTestController {
    
    @GetMapping(value = "/test")
    public String get() {
        return "GET Route WebClient";
    }
}
