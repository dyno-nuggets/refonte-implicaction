package com.dynonuggets.refonteimplicaction.utils;

public class Message {

    // Users messages
    public static final String USER_NOT_FOUND_MESSAGE = "No user found with id [%d]";

    // Jobs messages
    public static final String JOB_NOT_FOUND_MESSAGE = "No job found with id [%d]";

    // Subreddit messages
    public static final String SUBREDDIT_NOT_FOUND_MESSAGE = "No subreddit found with id [%s]";

    // Post messages
    public static final String POST_NOT_FOUND_MESSAGE = "No post found with id [%s]";
    public static final String POST_SHOULD_HAVE_A_NAME = "Unable to save post, a post should have a valid name";

    // Comment messages
    public static final String COMMENT_NOT_FOUND = "No comment found with id [%s]";

    // Vote messages
    public static final String USER_ALREADY_VOTE_FOR_POST = "Current user cannot vote more than once the same way for a single post";

    // File messages
    public static final String UNKNOWN_FILE_UPLOAD_MESSAGE = "Exception occured while uploading the file [%s]";
    public static final String FILE_SIZE_TOO_LARGE_MESSAGE = "Exception occured while uploading the file [%s] : maximum size (%d) exceeded";
    public static final String UNAUTHORIZED_CONTENT_TYPE_MESSAGE = "Exception occured while uploading the file [%s] : unauthorized content type";
    public static final String FILE_NOT_FOUND_MESSAGE = "No file found with id [%s]";

    public static final String BAD_CREDENTIAL_MESSAGE = "Nom d'utilisateur ou mot de passe incorrect.";
    public static final String USER_DISABLED_MESSAGE = "Votre compte n'a pas encore été activé.";

    private Message() {
        // Empeche la création d'une instance de la classe Message
    }
}
