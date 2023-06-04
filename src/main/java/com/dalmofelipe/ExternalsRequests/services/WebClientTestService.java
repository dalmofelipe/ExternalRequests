package com.dalmofelipe.ExternalsRequests.services;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.dalmofelipe.ExternalsRequests.dtos.Pokemon;
import com.dalmofelipe.ExternalsRequests.exceptions.PokeException;
import com.dalmofelipe.ExternalsRequests.exceptions.PokeNotFoundException;

import reactor.core.publisher.Mono;

@Service
public class WebClientTestService {

    private final String pokemonUrl = "https://pokeapi.co/api/v2/pokemon/";

    // configuração necessária, pois dependendo do pokemon pesquisado,
    // a pokeapi pode retornar muitos dados na response, estourando 
    // o limite padrão do springboot que é igual a 256 Kibibytes.
    final int size = 16 * 1024 * 1024; // 16MB
    final ExchangeStrategies strategies = ExchangeStrategies.builder()
        .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
        .build();

    private WebClient newClient(String baseUrl) {
        return WebClient
            .builder()
            .exchangeStrategies(strategies)
            .filter(errorHandler())
            .baseUrl(baseUrl)
            .build();
    }

    public static ExchangeFilterFunction errorHandler() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().is5xxServerError()) {
                return clientResponse
                    .bodyToMono(String.class)
                    .flatMap(errorBody -> Mono.error(new PokeException(HttpStatus.INTERNAL_SERVER_ERROR, errorBody)));
            } else if (clientResponse.statusCode().is4xxClientError()) {
                return clientResponse
                    .bodyToMono(String.class)
                    .flatMap(errorBody -> Mono.error(new PokeException(HttpStatus.BAD_REQUEST, errorBody)));
            } else {
                return Mono.just(clientResponse);
            }
        });
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
            .onStatus(httpStatus -> HttpStatus.BAD_REQUEST.equals(httpStatus), response -> {
                return Mono.error(new PokeException(HttpStatus.BAD_REQUEST, 
                    "verifique nome válido de um pokemon na uri /pokemon/{name}"));
            })
            .onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus), response -> {
                return Mono.error(new PokeNotFoundException("pokemon não encontrado"));
            })
            .onStatus(httpStatus -> HttpStatus.INTERNAL_SERVER_ERROR.equals(httpStatus), response -> {
                return Mono.error(new PokeException(HttpStatus.INTERNAL_SERVER_ERROR, 
                    "ERROR INTERNO"));
            })
            .bodyToMono(new ParameterizedTypeReference<Pokemon>() {})
            .block();
            // .bodyToMono(Pokemon.class);

        return Mono.just(pokemon);
    }   
}
