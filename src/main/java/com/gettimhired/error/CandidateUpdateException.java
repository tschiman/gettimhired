package com.gettimhired.error;

import org.springframework.http.HttpStatus;

public class CandidateUpdateException extends RuntimeException{

    private final HttpStatus httpStatus;


    public CandidateUpdateException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
