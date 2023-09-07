package com.demo.pokeapi.service;

import com.demo.pokeapi.entity.Pokemon;

import java.util.List;

public interface PokemonService {

    Pokemon getPokemonByName(String name);

    List<Pokemon> getPokemones();
}
