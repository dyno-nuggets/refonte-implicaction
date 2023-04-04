package com.dynonuggets.refonteimplicaction.filemanagement.utils;

import lombok.NoArgsConstructor;

import static com.dynonuggets.refonteimplicaction.core.utils.ApiUrls.PUBLIC_BASE_URI;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class FileUris {
    public static final String PUBLIC_FILE_BASE_URI = PUBLIC_BASE_URI + "/files";
    public static final String GET_FILE_BY_KEY = "/{objectKey}";
}
