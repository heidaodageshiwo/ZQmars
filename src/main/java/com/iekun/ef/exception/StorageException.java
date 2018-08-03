package com.iekun.ef.exception;

/**
 * Created by  feilong.cai on 2016/12/3.
 */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
