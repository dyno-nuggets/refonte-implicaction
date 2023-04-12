package com.dynonuggets.refonteimplicaction.filemanagement.utils;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class FileMessages {
    public static final String FILE_SIZE_TOO_LARGE_MESSAGE = "L'envoie du ficchier [%s] est impossible : taille maximum dépassée";
    public static final String UNAUTHORIZED_CONTENT_TYPE_MESSAGE = "L'envoie du ficchier [%s] est impossible : le format de fichier n'est pas autorisé";

}
