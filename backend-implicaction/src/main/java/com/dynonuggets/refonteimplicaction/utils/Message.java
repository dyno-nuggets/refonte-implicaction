package com.dynonuggets.refonteimplicaction.utils;

public class Message {

    // Jobs messages
    public static final String JOB_NOT_FOUND_MESSAGE = "No job found with id [%d]";

    // Subreddit messages
    public static final String SUBREDDIT_NOT_FOUND_MESSAGE = "No subreddit found with id [%s]";

    // Post messages
    public static final String POST_NOT_FOUND_MESSAGE = "No post found with id [%s]";

    // Comment messages
    public static final String COMMENT_NOT_FOUND = "No comment found with id [%s]";

    // Vote messages
    public static final String USER_ALREADY_VOTE_FOR_POST = "Current user cannot vote more than once the same way for a single post";

    // File messages
    public static final String UNKNOWN_FILE_UPLOAD_MESSAGE = "Exception occured while uploading the file [%s]";
    public static final String FILE_SIZE_TOO_LARGE_MESSAGE = "Exception occured while uploading the file [%s] : maximum size (%d) exceeded";
    public static final String UNAUTHORIZED_CONTENT_TYPE = "Exception occured while uploading the file [%s] : unauthorized content type";

    private Message() {
        // Empeche la création d'une instance de la classe Message
    }
}
