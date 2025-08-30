package com.paulosa.movieflix.repository;

import com.paulosa.movieflix.entity.Category;
import com.paulosa.movieflix.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {

    List<Movie> findMovieByCategories(List<Category> categories);
}
