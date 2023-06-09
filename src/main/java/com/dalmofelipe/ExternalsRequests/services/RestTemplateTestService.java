package com.dalmofelipe.ExternalsRequests.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.dalmofelipe.ExternalsRequests.dtos.Pokemon;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RestTemplateTestService {

    private RestTemplate restTemplate = new RestTemplate();
    private String pokeApiBaseUrl = "https://pokeapi.co/api/v2/pokemon/";

    public String getPokemonInfo(String pokeName) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<String>(headers);

        return restTemplate
            .exchange(pokeApiBaseUrl + pokeName, HttpMethod.GET, entity, String.class)
            .getBody();

    }

    public ResponseEntity<Pokemon> getPokemonJsonData(String pokeName) 
        throws JsonMappingException, JsonProcessingException {

        ResponseEntity<String> response
            = restTemplate.getForEntity(pokeApiBaseUrl + pokeName, String.class);
        Assert.isTrue(response.getStatusCode() == HttpStatus.OK, "request not ok");
        
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());

        JsonNode id = root.path("id");
        Assert.notNull(id.asInt(), "id não encontrado");
        Assert.isInstanceOf(Integer.class, id.asInt(), "id não parseado para numero Inteiro");

        JsonNode name = root.path("name");
        Assert.notNull(name.asText(), "name não encontrado");

        JsonNode baseExperience = root.path("base_experience");
        Assert.notNull(baseExperience.asInt(), "experiencia base não encontrado");
        Assert.isInstanceOf(Integer.class, baseExperience.asInt(), 
            "experiencia base não parseado para numero Inteiro");
        
        List<JsonNode> movesNodes = root.path("moves").findParents("move", null);
        Assert.notNull(movesNodes.toArray(), "lista de nomes dos ataques não encontrado");
        
        Pokemon pokemon = new Pokemon();
        pokemon.setId(id.asInt());
        pokemon.setName(name.asText());
        pokemon.setBaseExperience(baseExperience.asInt());
        pokemon.setMoves(movesNodes);
        // se inserir moves com setMoves, não será necessário usar updateMovesNamesList
        //pokemon.updateMovesNamesList();

        return new ResponseEntity<Pokemon>(pokemon, response.getHeaders(), HttpStatus.OK);
    }
    
    public ResponseEntity<Pokemon> getPokemonJsonToClass(String pokeName) 
        throws JsonMappingException, JsonProcessingException {

        ResponseEntity<Pokemon> response
            = restTemplate.getForEntity(pokeApiBaseUrl + pokeName, Pokemon.class);

        Assert.isTrue(response.getStatusCode() == HttpStatus.OK, "request not ok");

        return response;
    }
}
