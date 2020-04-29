package com.movie;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController
{

    private MovieService movieService;

    public MovieController(MovieService service)
    {
        movieService = service;
    }

    @GetMapping("/movies")
    public List<Movie> getAllMovies()
    {
        return movieService.getAllMovies();
    }

    @GetMapping("/movie/{id}")
    public Movie getMovie(@PathVariable Long id)
    {
        return movieService.getMovie(id);
    }

    @PostMapping("/movie")
    public Movie addMovie(@RequestParam(name = "name", required = true) String name)
    {
        return movieService.addMovie(name);
    }

    @DeleteMapping("/movie/{id}")
    public void deleteMovie(@PathVariable Long id)
    {
        movieService.deleteMovie(id);
    }

}
