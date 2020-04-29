package com.movie;

import java.util.List;
import java.util.Optional;

public interface MovieService
{

    List<Movie> getAllMovies();

    Movie getMovie(Long id);

    Movie addMovie(String name);

    void deleteMovie(Long id);
}
