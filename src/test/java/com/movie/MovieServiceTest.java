package com.movie;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringJUnit4ClassRunner.class)
public class MovieServiceTest
{

    @Mock
    MovieRepository movieRepository;

    MovieService movieService;

    List<Movie> movies = new ArrayList<>();

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        movieService = new MovieServiceImpl(movieRepository);
    }

    @Test
    public void testGetMovies()
    {
        movies.add(createNewMovieObject());
        Mockito.when(movieRepository.findAll()).thenReturn(movies);
        assertEquals(movies, movieService.getAllMovies());
    }

    @Test
    public void testGetMoviesReturnEmptyList()
    {
        Mockito.when(movieRepository.findAll()).thenReturn(movies);
        assertEquals(movies, movieService.getAllMovies());
    }


    @Test
    public void testGetByIdWithValidId()
    {
        Movie m = createNewMovieObject();
        movies.add(m);
        Mockito.when(movieRepository.findById(any(Long.class))).thenReturn(Optional.of(m));
        assertEquals(m, movieService.getMovie(1l));
    }

    private Movie createNewMovieObject()
    {
        Movie m = new Movie();
        m.setId(1l);
        m.setName("ABC");
        return m;
    }

    @Test(expected = MovieException.class)
    public void testGetByIdWithInValidId()
    {
        Mockito.when(movieRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        movieService.getMovie(1l);
    }

    @Test
    public void testAddNewMovie()
    {
        Movie movie = createNewMovieObject();
        Mockito.when(movieRepository.save(any(Movie.class))).thenReturn(movie);
        assertEquals(movie, movieService.addMovie("ABC"));
    }

    @Test
    public void testDeleteValidMovie()
    {
        Mockito.when(movieRepository.findById(any(Long.class))).thenReturn(Optional.of(createNewMovieObject()));
        movieService.deleteMovie(1l);
    }

    @Test(expected = MovieException.class)
    public void testDeleteInValidMovie()
    {
        Mockito.when(movieRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        movieService.deleteMovie(1l);
    }
}
