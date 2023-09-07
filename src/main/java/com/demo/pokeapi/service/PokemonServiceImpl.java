package com.demo.pokeapi.service;

import com.demo.pokeapi.entity.Pokemon;
import com.demo.pokeapi.entity.PokemonListResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {
    @Override
    public Pokemon getPokemonByName(String name) {
        final String API_URL = "https://pokeapi.co/api/v2/pokemon/";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(API_URL + name, Pokemon.class);
    }

    @Override
    public List<Pokemon> getPokemones() {
        final String API_URL = "https://pokeapi.co/api/v2/pokemon?limit=10&offset=0";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<PokemonListResponse> responseEntity = restTemplate.exchange(
                API_URL,
                HttpMethod.GET,
                null,
                PokemonListResponse.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            PokemonListResponse listResponse = responseEntity.getBody();
            if (listResponse != null && listResponse.getResults() != null) {
                return listResponse.getResults().stream()
                        .map(pokemon -> restTemplate.getForObject(pokemon.getUrl(), Pokemon.class))
                        .collect(Collectors.toList());
            }
        }

        return Collections.emptyList();
    }


}