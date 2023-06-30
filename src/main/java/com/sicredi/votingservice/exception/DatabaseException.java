package com.sicredi.votingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.sicredi.votingservice.constants.VotingServiceConstants.DATABASE_EXCEPTION;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DatabaseException extends RuntimeException {
    public DatabaseException() {
        super(DATABASE_EXCEPTION);
    }
}
