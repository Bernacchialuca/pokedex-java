package com.demo.pokeapi.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PokemonSpeciesResponse {
    private List<FlavorTextEntry> flavor_text_entries;
}
