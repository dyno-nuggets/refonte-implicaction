package com.dynonuggets.refonteimplicaction.filemanagement.utils;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class FileUris {
    public static final String FILE_BASE_URI = "/api/files";
    public static final String GET_FILE_BY_KEY = "/{objectKey}";
}
