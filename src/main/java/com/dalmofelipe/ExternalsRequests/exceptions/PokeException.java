package com.dalmofelipe.ExternalsRequests.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class PokeException extends ResponseStatusException {

    private static final long serialVersionUID = 1L;

    public PokeException(HttpStatusCode status, String message) {
        super(status, message);
    }
}