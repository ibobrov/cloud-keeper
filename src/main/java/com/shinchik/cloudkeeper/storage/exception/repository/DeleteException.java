package com.shinchik.cloudkeeper.storage.exception.repository;

public class DeleteException extends MinioRepositoryException{
    public DeleteException(String message) {
        super(message);
    }

    public DeleteException(Throwable cause) {
        super(cause);
    }
}
