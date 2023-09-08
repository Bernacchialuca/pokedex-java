package com.demo.pokeapi.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PokemonAbilityResponse {
    private List<Ability> abilities;
}
