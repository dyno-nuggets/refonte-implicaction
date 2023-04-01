package com.dynonuggets.refonteimplicaction.core.utils;


/**
 * @deprecated depuis la v.2003 ces messages doivent être déplacer dans les classes correspondantes afin de limiter le couplage
 */
@Deprecated(since = "v.2023")
public class Message {
    // Jobs messages
    @Deprecated(since = "v.2023")
    public static final String JOB_NOT_FOUND_MESSAGE = "No job found with id [%d]";

    // File messages
    @Deprecated(since = "v.2023")
    public static final String UNKNOWN_FILE_UPLOAD_MESSAGE = "Exception occured while uploading the file [%s]";
    @Deprecated(since = "v.2023")
    public static final String FILE_SIZE_TOO_LARGE_MESSAGE = "Exception occured while uploading the file [%s] : maximum size (%d) exceeded";
    @Deprecated(since = "v.2023")
    public static final String UNAUTHORIZED_CONTENT_TYPE_MESSAGE = "Exception occured while uploading the file [%s] : unauthorized content type";
    @Deprecated(since = "v.2023")
    public static final String FILE_NOT_FOUND_MESSAGE = "No file found with id [%s]";

    // JobApplication messages
    @Deprecated(since = "v.2023")
    public static final String APPLY_NOT_FOUND_MESSAGE = "No apply found with id [%d]";
    @Deprecated(since = "v.2023")
    public static final String APPLY_NOT_FOUND_WITH_JOB_AND_USER = "No apply found for user [%d] and jobId [%d]";
    @Deprecated(since = "v.2023")
    public static final String APPLY_ALREADY_EXISTS_FOR_JOB = "Unable to apply, apply already exists with jobId [%d]";
    @Deprecated(since = "v.2023")
    public static final String USER_DISABLED_MESSAGE = "Votre compte n'a pas encore été activé.";


    private Message() {
        // Empeche la création d'une instance de la classe Message
    }
}
