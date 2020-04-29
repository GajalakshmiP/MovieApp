package com.movie;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MovieException extends RuntimeException
{

    public MovieException(String message)
    {
        super(message);
    }
}
