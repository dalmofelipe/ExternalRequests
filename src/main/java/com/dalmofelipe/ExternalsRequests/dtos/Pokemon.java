package com.dalmofelipe.ExternalsRequests.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class Pokemon {

    private Integer id;
    private String name;

    @JsonProperty("base_experience")
    private Integer baseExperience;

    private List<JsonNode> moves;
    
    private List<String> namesMoves;

    public Pokemon() { }
    public Pokemon(Integer id, String name, Integer baseExperience, List<JsonNode> moves) {
        this.id = id;
        this.name = name;
        this.baseExperience = baseExperience;
        this.moves = moves;
        this.namesMoves = new ArrayList<>();
    }

    public void updateMovesNamesList() {
        this.namesMoves = this.moves
            .stream()
            .map(move -> move.path("move").path("name").asText())
            .collect(Collectors.toList());
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getBaseExperience() {
        return baseExperience;
    }
    public void setBaseExperience(Integer baseExperience) {
        this.baseExperience = baseExperience;
    }
    public void setMoves(List<JsonNode> moves) {
        this.moves = moves;
        this.updateMovesNamesList(); 
    }
    public List<String> getNamesMoves() {
        return namesMoves;
    }
    
}
