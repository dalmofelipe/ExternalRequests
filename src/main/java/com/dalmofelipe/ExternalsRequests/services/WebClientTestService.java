package com.dalmofelipe.ExternalsRequests.services;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.dalmofelipe.ExternalsRequests.dtos.Pokemon;
import com.dalmofelipe.ExternalsRequests.exceptions.PokeException;

import reactor.core.publisher.Mono;

@Service
public class WebClientTestService {

    private final String pokemonUrl = "https://pokeapi.co/api/v2/pokemon/";

    private WebClient newClient(String baseUrl) {
        return WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
    }

    public Mono<Pokemon> searchPokemonByName(String name) {
        if(name == null) {
            throw new PokeException(HttpStatus.BAD_REQUEST, 
                "nome é um campo obrigatório");
        }

        Pokemon pokemon = this.newClient(pokemonUrl)
            .get()
            .uri(builder -> builder.path(name.toLowerCase()).build())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Pokemon>() {})
            .block();
            // .bodyToMono(Pokemon.class);

        return Mono.just(pokemon);
    }   
}
