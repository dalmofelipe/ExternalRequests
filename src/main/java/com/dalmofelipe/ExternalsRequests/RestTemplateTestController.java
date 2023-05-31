package com.dalmofelipe.ExternalsRequests;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest-template")
public class RestTemplateTestController {
    
    @GetMapping(value = "/test")
    public String get() {
        return "GET Route RestTemplate";
    }
}
