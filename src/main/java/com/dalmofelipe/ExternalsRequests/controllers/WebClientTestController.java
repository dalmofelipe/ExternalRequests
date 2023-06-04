package com.dalmofelipe.ExternalsRequests.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dalmofelipe.ExternalsRequests.dtos.Pokemon;
import com.dalmofelipe.ExternalsRequests.services.WebClientTestService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/web-client")
public class WebClientTestController {
    
    private WebClientTestService pokeService;

    public WebClientTestController(WebClientTestService pokeService) {
        this.pokeService = pokeService;
    }

    @GetMapping("/pokemon/{name}")
    public ResponseEntity<Mono<Pokemon>> searchPokemon(@PathVariable String name) {
        Mono<Pokemon> pokemon = pokeService.searchPokemonByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(pokemon);
    }
}
