package com.demo.pokeapi.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PokemonListResponse {
    private int count;
    private String next;
    private String previous;
    private List<PokemonListItem> results;

}
