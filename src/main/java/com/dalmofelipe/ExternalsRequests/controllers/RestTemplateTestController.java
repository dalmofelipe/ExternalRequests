package com.dalmofelipe.ExternalsRequests.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dalmofelipe.ExternalsRequests.services.RestTemplateTestService;

@RestController
@RequestMapping(value = "/rest-template")
public class RestTemplateTestController {

    private RestTemplateTestService service;

    RestTemplateTestController(RestTemplateTestService service) {
        this.service = service;
    }

    @RequestMapping(value = "/pokemon/{pokeName}")
    public String pokemonInfo(@PathVariable String pokeName) {
        return this.service.getPokemonInfoByName(pokeName);
    }
}
