package com.pokemonreview.api.service;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.ReviewDto;
import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.models.Review;
import com.pokemonreview.api.repository.PokemonRepository;
import com.pokemonreview.api.repository.ReviewRepository;
import com.pokemonreview.api.service.impl.ReviewServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTests {
    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private ReviewServiceImpl reviewServiceImpl;

    private Pokemon pokemon;
    private Review review;
    private PokemonDto pokemonDto;
    private ReviewDto reviewDto;

    @BeforeEach
    public void init(){
         pokemon = Pokemon.builder().name("Pikachu").type("Electro").build();
         pokemonDto = PokemonDto.builder().name("Pokemon").type("type").build();
         review = Review.builder().title("title").content("Content").stars(5).build();
         reviewDto = ReviewDto.builder().title("titleDto").content("ContentDto").stars(5).build();
    }

    @Test
    public void ReviewServiceCreateReview(){
        when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.of(pokemon));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewDto savedReview = reviewServiceImpl.createReview(pokemon.getId(),reviewDto);

        Assertions.assertThat(savedReview).isNotNull();
    }

    @Test
    public void ReviewServiceGetReviews(){
        int reviewId = 1;

        when(reviewRepository.findByPokemonId(reviewId)).thenReturn(Arrays.asList(review));

        List<ReviewDto> reviewDtos = reviewServiceImpl.getReviewsByPokemonId(reviewId);

        Assertions.assertThat(reviewDtos).isNotNull();

    }

    @Test
    public void ReviewServiceGetReviewById(){
        int reviewId = 1;
        int pokemonId = 1;
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.ofNullable(review));

        review.setPokemon(pokemon);
        ReviewDto reviewDto1 = reviewServiceImpl.getReviewById(reviewId,pokemonId);

        Assertions.assertThat(reviewDto1).isNotNull();
    }


    @Test
    public void ReviewServiceUpdateReview(){
        int reviewId = 1;
        int pokemonId = 1;
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.ofNullable(review));

        review.setPokemon(pokemon);

        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewDto updateReview = reviewServiceImpl.updateReview(pokemonId,reviewId,reviewDto);

        Assertions.assertThat(updateReview).isNotNull();
    }
    @Test
    public void ReviewServiceDeleteReview(){
        int reviewId = 1;
        int pokemonId = 1;
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.ofNullable(review));

        review.setPokemon(pokemon);

        assertAll(()->{
            reviewServiceImpl.deleteReview(pokemonId,reviewId);
        });
    }
}
