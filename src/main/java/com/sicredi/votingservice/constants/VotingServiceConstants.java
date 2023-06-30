package com.sicredi.votingservice.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class VotingServiceConstants {
    public static final String TOPIC_EXIST_EXCEPTION = "Voting topic doesn't exist!";
    public static final String SESSION_EXIST_EXCEPTION = "The session doesn't exist or has already expired!";
    public static final String ILLEGAL_VOTE_EXCEPTION = "The vote has already been computed in this topic, use another valid document!";
    public static final String CREATE_TOPIC_SUCCESSFULLY = "Topic successfully created.";
    public static final String OPENING_SESSION_SUCCESSFULLY = "Session opened successfully.";
    public static final String COMPUTED_VOTE_SUCCESSFULLY = "Vote computed with success.";
    public static final String DATABASE_EXCEPTION = "Database malfunction, try again later.";
}
