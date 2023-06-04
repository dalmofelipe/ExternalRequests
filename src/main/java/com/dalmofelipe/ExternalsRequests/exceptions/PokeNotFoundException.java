package com.dalmofelipe.ExternalsRequests.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PokeNotFoundException extends ResponseStatusException {

    private static final long serialVersionUID = 1L;

    public PokeNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}