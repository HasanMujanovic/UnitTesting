package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Review;
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
public class ReviewRepositoryTests {
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void ReviewRepositorySaveAll(){
        Review review = Review.builder().title("title").content("Content").stars(5).build();

        Review review1 = reviewRepository.save(review);

        Assertions.assertThat(review1).isNotNull();
        Assertions.assertThat(review1.getId()).isGreaterThan(0);
    }

    @Test
    public void ReviewRepositoryFindById(){
        Review review = Review.builder().title("title").content("Content").stars(5).build();
        reviewRepository.save(review);

        Review findByIdReview = reviewRepository.findById(review.getId()).get();

        Assertions.assertThat(findByIdReview).isNotNull();
        Assertions.assertThat(findByIdReview.getId()).isEqualTo(review.getId());
    }

    @Test
    public void ReviewRepositoryFindAll(){
        Review review = Review.builder().title("title").content("Content").stars(5).build();
        reviewRepository.save(review);

        List<Review> reviews = reviewRepository.findAll();

        Assertions.assertThat(reviews).isNotNull();
        Assertions.assertThat(reviews.size()).isEqualTo(1);
    }

    @Test
    public void ReviewRepositoryUpdate(){
        Review review = Review.builder().title("title").content("Content").stars(5).build();
        reviewRepository.save(review);

        Review review1 = reviewRepository.findById(review.getId()).get();
        review1.setContent("Content1");
        review1.setTitle("title1");
        review1.setStars(2);

        Review updatedReview = reviewRepository.findById(review1.getId()).get();

        Assertions.assertThat(updatedReview).isNotNull();
        Assertions.assertThat(updatedReview.getTitle()).isEqualTo("title1");
        Assertions.assertThat(updatedReview.getId()).isEqualTo(review.getId());

    }

    @Test
    public void ReviewRepositoryDelete(){
        Review review = Review.builder().title("title").content("Content").stars(5).build();
        reviewRepository.save(review);

        reviewRepository.deleteById(review.getId());

        Optional<Review> deletedReview = reviewRepository.findById(review.getId());

        Assertions.assertThat(deletedReview).isEmpty();

    }
}
