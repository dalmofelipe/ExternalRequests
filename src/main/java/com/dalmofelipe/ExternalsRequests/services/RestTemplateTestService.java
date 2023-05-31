package com.dalmofelipe.ExternalsRequests.services;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateTestService {

    private RestTemplate restTemplate = new RestTemplate();
    private String pokeApiBaseUrl = "https://pokeapi.co/api/v2/pokemon/";

    public String getPokemonInfoByName(String pokeName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<String>(headers);

        return restTemplate
            .exchange(pokeApiBaseUrl + pokeName, HttpMethod.GET, entity, String.class)
            .getBody();
    }
}
