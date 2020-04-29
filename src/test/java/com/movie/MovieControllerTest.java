package com.movie;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest
public class MovieControllerTest
{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    public void testGetAllMovies() throws Exception
    {
        Movie m = createNewMovieObject();
        Mockito.when(movieService.getAllMovies()).thenReturn(Arrays.asList(m));
        mockMvc.perform(get("/movies")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));

        Mockito.verify(movieService).getAllMovies();
    }

    @Test
    public void testGetAllMoviesReturnsEmptyListIfDBIsEmpty() throws Exception
    {
        Mockito.when(movieService.getAllMovies()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/movies")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));

        Mockito.verify(movieService).getAllMovies();
    }

    @Test
    public void testGetMovieByValidIdReturnsMovie() throws Exception
    {
        Movie m = createNewMovieObject();
        Mockito.when(movieService.getMovie(1l)).thenReturn(m);
        mockMvc.perform(get("/movie/1")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("ABC")));

        Mockito.verify(movieService).getMovie(1l);

    }

    @Test
    public void testGetMovieByInvalidIdThrowsException() throws Exception
    {
        Mockito.when(movieService.getMovie(1l)).thenThrow(new MovieException("Movie not found"));
        mockMvc.perform(get("/movie/1")).andExpect(status().is4xxClientError());
        Mockito.verify(movieService).getMovie(1l);

    }

    @Test
    public void testAddMovie() throws Exception
    {
        Movie m = createNewMovieObject();
        Mockito.when(movieService.addMovie("ABC")).thenReturn(m);
        mockMvc.perform(post("/movie")
        .param("name", "ABC")).andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.name", Matchers.is("ABC")))
        .andExpect(jsonPath("$.id", Matchers.is(1)));
        Mockito.verify(movieService, Mockito.times(1)).addMovie("ABC");

    }

    @Test
    public void testDeleteMovieWithValidInputId() throws Exception
    {
        Movie m = createNewMovieObject();
        Mockito.doNothing().when(movieService).deleteMovie(1l);

        mockMvc.perform(delete("/movie/1")).andExpect(status().isOk());

        Mockito.verify(movieService, Mockito.times(1)).deleteMovie(1l);

    }

    @Test
    public void testDeleteMovieWithInvalidInputId() throws Exception
    {
        Mockito.doThrow(new MovieException("Movie not found")).when(movieService).deleteMovie(1l);
        mockMvc.perform(delete("/movie/1")).andExpect(status().is4xxClientError());

        Mockito.verify(movieService, Mockito.times(1)).deleteMovie(1l);

    }
    private Movie createNewMovieObject()
    {
        Movie m = new Movie();
        m.setId(1l);

        m.setName("ABC");
        return m;
    }

}
