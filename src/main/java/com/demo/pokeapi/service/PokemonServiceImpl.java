package com.demo.pokeapi.service;

import com.demo.pokeapi.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {

    @Override
    public Pokemon getPokemonByName(String name) {
        String nameToLowerCase = name.toLowerCase();
        final String API_URL = "https://pokeapi.co/api/v2/pokemon/";
        RestTemplate restTemplate = new RestTemplate();
        Pokemon pokemon = restTemplate.getForObject(API_URL + nameToLowerCase, Pokemon.class);

        if (pokemon != null) {
            addDescriptionToPokemon(pokemon, nameToLowerCase, restTemplate);
            addDescriptionToPokemon(pokemon,nameToLowerCase,restTemplate);
        }

        return pokemon;
    }

    @Override
    public List<Pokemon> getPokemones() {
        final String API_URL = "https://pokeapi.co/api/v2/pokemon?limit=10&offset=0";
        RestTemplate restTemplate = new RestTemplate();

        PokemonListResponse listResponse = restTemplate.getForObject(API_URL, PokemonListResponse.class);

        if (listResponse != null && listResponse.getResults() != null) {
            return listResponse.getResults().stream()
                    .map(pokemon -> restTemplate.getForObject(pokemon.getUrl(), Pokemon.class))
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    private void addDescriptionToPokemon(Pokemon pokemon, String nameToLowerCase, RestTemplate restTemplate) {
        String speciesUrl = "https://pokeapi.co/api/v2/pokemon-species/" + nameToLowerCase;
        PokemonSpeciesResponse speciesResponse = restTemplate.getForObject(speciesUrl, PokemonSpeciesResponse.class);

        if (speciesResponse != null && speciesResponse.getFlavor_text_entries() != null) {
            String description = speciesResponse.getFlavor_text_entries().stream()
                    .filter(entry -> "en".equals(entry.getLanguage().getName()))
                    .findFirst()
                    .map(FlavorTextEntry::getFlavor_text)
                    .orElse("");
            String cleanedDescription = removeSpecialCharacters(description);
            pokemon.setDescription(cleanedDescription);
        }

    }

    public void addAbilitiesToPokemon(Pokemon pokemon, String nameToLowerCase, RestTemplate restTemplate) {
        String pokemonUrl = "https://pokeapi.co/api/v2/pokemon/" + nameToLowerCase;
        PokemonAbilityResponse abilityResponse = restTemplate.getForObject(pokemonUrl, PokemonAbilityResponse.class);

        if (abilityResponse != null && abilityResponse.getAbilities() != null) {
            List<Ability> abilities = abilityResponse.getAbilities().stream()
                    .map(ability -> {
                        Ability pokemonAbility = new Ability();
                        AbilityDetails abilityDetails = ability.getAbility();
                        pokemonAbility.setAbility(abilityDetails);
                        return pokemonAbility;
                    })
                    .collect(Collectors.toList());
            pokemon.setAbilities(abilities);
        }
    }


    public String removeSpecialCharacters(String input) {
        return input.replaceAll("\f", " ");
    }

}
