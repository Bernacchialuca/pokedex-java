package com.demo.pokeapi.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Pokemon {
    private Long id;
    private String name;
    private List<PokemonType> types;
    private PokemonSprites sprites;
    private String description;
    private List<Ability> abilities;
}