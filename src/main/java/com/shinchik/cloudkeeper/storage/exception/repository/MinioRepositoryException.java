package com.shinchik.cloudkeeper.storage.exception.repository;

public class MinioRepositoryException extends RuntimeException{
    public MinioRepositoryException(String message) {
        super(message);
    }

    public MinioRepositoryException(Throwable cause) {
        super(cause);
    }
}
