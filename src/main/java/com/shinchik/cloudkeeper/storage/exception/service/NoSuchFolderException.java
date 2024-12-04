package com.shinchik.cloudkeeper.storage.exception.service;

public class NoSuchFolderException extends NoSuchObjectException{
    public NoSuchFolderException(String message) {
        super(message);
    }

    public NoSuchFolderException(Throwable cause) {
        super(cause);
    }
}
