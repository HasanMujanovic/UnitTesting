package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Pokemon;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

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

    @Test
    public void PokemonRepositoryGetById(){
        Pokemon pokemon1 = Pokemon.builder().name("Charizard").type("Fire").build();
        pokemonRepository.save(pokemon1);

        Pokemon getByIdPokemon = pokemonRepository.findById(pokemon1.getId()).get();

        Assertions.assertThat(getByIdPokemon).isNotNull();
        Assertions.assertThat(getByIdPokemon.getId()).isEqualTo(pokemon1.getId());
    }

    @Test
    public void PokemonRepositoryFindByType(){
        Pokemon pokemon1 = Pokemon.builder().name("Charizard").type("Fire").build();
        pokemonRepository.save(pokemon1);

        Pokemon findByTypePokemon = pokemonRepository.findByType(pokemon1.getType()).get();

        Assertions.assertThat(findByTypePokemon).isNotNull();
        Assertions.assertThat(findByTypePokemon.getType()).isEqualTo(pokemon1.getType());
    }

    @Test
    public void PokemonRepositoryUpdate(){
        Pokemon pokemon1 = Pokemon.builder().name("Charizard").type("Fire").build();
        pokemonRepository.save(pokemon1);

        Pokemon savedPokemon = pokemonRepository.findById(pokemon1.getId()).get();

        savedPokemon.setName("Raichu");
        savedPokemon.setType("Electro");

        Pokemon updatedPokemon = pokemonRepository.findById(savedPokemon.getId()).get();

        Assertions.assertThat(updatedPokemon.getType()).isNotNull();
        Assertions.assertThat(updatedPokemon.getName()).isNotNull();

    }

    @Test
    public void PokemonRepositoryDelete(){
        Pokemon pokemon1 = Pokemon.builder().name("Charizard").type("Fire").build();
        pokemonRepository.save(pokemon1);

        pokemonRepository.deleteById(pokemon1.getId());

        Optional<Pokemon> optionalPokemon = pokemonRepository.findById(pokemon1.getId());

        Assertions.assertThat(optionalPokemon).isEmpty();
    }
}
