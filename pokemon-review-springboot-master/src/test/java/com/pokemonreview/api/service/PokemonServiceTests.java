package com.pokemonreview.api.service;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.PokemonResponse;
import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.repository.PokemonRepository;
import com.pokemonreview.api.service.impl.PokemonServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTests {
    @Mock
    PokemonRepository pokemonRepository;

    @InjectMocks
    private PokemonServiceImpl pokemonServiceImpl;

    @Test
    public void PokemonServiceCreatePokemon(){
        Pokemon pokemon = Pokemon.builder().name("Pikachu").type("Electro").build();
        PokemonDto pokemonDto = PokemonDto.builder().name("Pokemon").type("type").build();

        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

        PokemonDto savePokemon = pokemonServiceImpl.createPokemon(pokemonDto);

        Assertions.assertThat(savePokemon).isNotNull();

    }

    @Test
    public void PokemonServiceGetAllPokemon(){
        Page<Pokemon> pokemons = Mockito.mock(Page.class);

        when(pokemonRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pokemons);

        PokemonResponse pokemonResponse = pokemonServiceImpl.getAllPokemon(1,5);

        Assertions.assertThat(pokemonResponse).isNotNull();
        Assertions.assertThat(pokemonResponse.getPageNo()).isEqualTo(pokemons.getNumber());

    }

    @Test
    public void PokemonServiceGetPokemonById(){
//
//        Pokemon pokemon = Mockito.mock(Pokemon.class);
//
//        when(pokemonRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(pokemon));
//

        Pokemon pokemon = Pokemon.builder().name("name").type("type").build();

        when(pokemonRepository.findById(2)).thenReturn(Optional.ofNullable(pokemon));

        PokemonDto getPokemonById = pokemonServiceImpl.getPokemonById(2);

        Assertions.assertThat(getPokemonById).isNotNull();

    }

    @Test
    public void PokemonServiceUpdatePokemon(){
        PokemonDto pokemonDto = PokemonDto.builder().name("name").type("type").build();
        Pokemon pokemon = Pokemon.builder().name("name2").type("type2").build();

        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));

        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

        PokemonDto updatedPokemon = pokemonServiceImpl.updatePokemon(pokemonDto,1);

        Assertions.assertThat(updatedPokemon).isNotNull();
    }


    @Test
    public void PokemonServiceDelete(){
        Pokemon pokemon = Pokemon.builder().name("name2").type("type2").build();

        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));

        assertAll(() -> {
            pokemonServiceImpl.deletePokemonId(1);

            verify(pokemonRepository, times(1)).delete(pokemon);
        });
    }
}
