package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Pokemon;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PokemonRepositoryTests {
    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    public void PokemonRepositorySaveAll(){
        // Arrange
        Pokemon pokemon = Pokemon.builder().name("Pikachu").type("Electric").build();

        //Act
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        //Assert
        Assertions.assertThat(savedPokemon).isNotNull();
        Assertions.assertThat(savedPokemon.getId()).isGreaterThan(0);
    }

    @Test
    public void PokemonRepositoryFindAll(){
        Pokemon pokemon1 = Pokemon.builder().name("Charizard").type("Fire").build();
        Pokemon pokemon2 = Pokemon.builder().name("Bulbasaur").type("Nature").build();
        
        pokemonRepository.save(pokemon1);
        pokemonRepository.save(pokemon2);

        List<Pokemon> pokemons = pokemonRepository.findAll();
        
        Assertions.assertThat(pokemons).isNotNull();
        Assertions.assertThat(pokemons.size()).isEqualTo(2);
        
    }
}
