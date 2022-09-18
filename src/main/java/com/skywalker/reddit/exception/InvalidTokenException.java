package com.skywalker.reddit.exception;

public class InvalidTokenException extends RuntimeException{

    public InvalidTokenException(String msg){
        super(msg);
    }
}
