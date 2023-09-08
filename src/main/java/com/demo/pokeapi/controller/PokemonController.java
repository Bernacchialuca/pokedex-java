package com.demo.pokeapi.controller;

import com.demo.pokeapi.entity.Pokemon;
import com.demo.pokeapi.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Controller
public class PokemonController {
    private final PokemonService pokemonService;

    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/pokemon")
    public String getPokemon(@RequestParam(name = "name") String name, Model model) {
        try {
            Pokemon pokemon = this.pokemonService.getPokemonByName(name);
            model.addAttribute("pokemon", pokemon);
            return "pokemon";
        } catch (HttpClientErrorException.NotFound ex) {
            model.addAttribute("error", "Pokemon " + name + " was not found. 😖");
            return "error";
        }
    }

    @GetMapping({"/", "/pokedex"})
    public String getPokemons(Model model) {
        List<Pokemon> pokemones = this.pokemonService.getPokemones();
        model.addAttribute("pokemones", pokemones);
        return "pokedex";
    }

    @GetMapping("/pokemon/details/{name}")
    public String verDetallePokemon(@PathVariable(name = "name") String name, Model model) {
        try {
            Pokemon pokemon = this.pokemonService.getPokemonByName(name);
            model.addAttribute("pokemon", pokemon);
            return "pokemon-details";
        } catch (HttpClientErrorException.NotFound ex) {
            model.addAttribute("error", "Pokemon " + name + " was not found. 😖");
            return "error";
        }
    }
}