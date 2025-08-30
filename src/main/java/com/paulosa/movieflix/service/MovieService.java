package com.paulosa.movieflix.service;

import com.paulosa.movieflix.entity.Category;
import com.paulosa.movieflix.entity.Movie;
import com.paulosa.movieflix.entity.Streaming;
import com.paulosa.movieflix.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final CategoryService categoryService;
    private final StreamingService streamingService;

    public Movie save(Movie movie){
        movie.setCategories(this.findCategories(movie.getCategories()));
        movie.setStreamings(this.findStreamings(movie.getStreamings()));
        return movieRepository.save(movie);
    }

    public List<Movie> findAll(){
        return movieRepository.findAll();
    }

    public Optional<Movie> findById(Long id){
        return movieRepository.findById(id);
    }

    public List<Movie> findByCategory(Long categoryId){
        return movieRepository.findMovieByCategories(List.of(Category.builder().id(categoryId).build()));
    }

    public Optional<Movie> update(Long movieId, Movie updatedMovie){
        Optional<Movie> optMovie = movieRepository.findById(movieId);
        if (optMovie.isPresent()){

            List<Category> categories = this.findCategories(updatedMovie.getCategories());
            List<Streaming> streamings = this.findStreamings(updatedMovie.getStreamings());

           Movie movie = optMovie.get();
           movie.setTitle(updatedMovie.getTitle());
           movie.setDescription(updatedMovie.getDescription());
           movie.setReleaseDate(updatedMovie.getReleaseDate());
           movie.setRating(updatedMovie.getRating());

           movie.getCategories().clear();
           movie.getCategories().addAll(categories);

           movie.getStreamings().clear();
           movie.getStreamings().addAll(streamings);

           movieRepository.save(movie);
           return Optional.of(movie);
        }
        return Optional.empty();
    }

    public void delete(Long movieId){
        movieRepository.deleteById(movieId);
    }

    private List<Category> findCategories(List<Category> categories){
        List<Category> categoriesFound = new ArrayList<>();
        categories.forEach(category -> categoryService.findById(category.getId()).ifPresent(categoriesFound::add));
        return categoriesFound;
        }

    private List<Streaming> findStreamings(List<Streaming> streamings){
        List<Streaming> streamingsFound = new ArrayList<>();
        streamings.forEach(streaming -> streamingService.findById(streaming.getId()).ifPresent(streamingsFound::add));
        return streamingsFound;
    }
    }


