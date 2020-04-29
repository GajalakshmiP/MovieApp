package com.movie;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService
{

    private MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository)
    {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies()
    {
        return (List<Movie>) movieRepository.findAll();
    }

    @Override
    public Movie getMovie(Long id)
    {
        return movieRepository.findById(id).orElseThrow(() -> new MovieException("Movie Not found"));
    }

    @Override
    //No validation for unique movie names. Can add unique database column or validate for same names in code
    public Movie addMovie(String name)
    {
        Movie newMovie = new Movie();
        newMovie.setName(name);
        return movieRepository.save(newMovie);
    }

    @Override
    public void deleteMovie(Long id)
    {
        movieRepository.delete(getMovie(id));
    }


}
