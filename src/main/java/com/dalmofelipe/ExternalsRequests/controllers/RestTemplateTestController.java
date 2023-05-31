package com.dalmofelipe.ExternalsRequests.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dalmofelipe.ExternalsRequests.dtos.Pokemon;
import com.dalmofelipe.ExternalsRequests.services.RestTemplateTestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping(value = "/rest-template")
public class RestTemplateTestController {

    private RestTemplateTestService service;

    public RestTemplateTestController(RestTemplateTestService service) {
        this.service = service;
    }

    @GetMapping(value = "/pokemon/{pokeName}")
    public String pokemonInfo(@PathVariable String pokeName) {
        return this.service.getPokemonInfo(pokeName.toLowerCase());
    }

    @GetMapping(value = "/pokemon/json/{pokeName}")
    public ResponseEntity<Pokemon> pokemonInfoFromJson(@PathVariable String pokeName) 
        throws JsonMappingException, JsonProcessingException {

        return this.service.getPokemonJsonData(pokeName.toLowerCase());
    }

    @GetMapping(value = "/pokemon/class/{pokeName}")
    public ResponseEntity<Pokemon> pokemonInfoToClass(@PathVariable String pokeName) 
        throws JsonMappingException, JsonProcessingException {

        return this.service.getPokemonJsonToClass(pokeName.toLowerCase());
    }

}
