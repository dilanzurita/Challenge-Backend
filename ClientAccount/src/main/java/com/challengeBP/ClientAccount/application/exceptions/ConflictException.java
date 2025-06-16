package com.challengeBP.ClientAccount.application.exceptions;

public class ConflictException extends  RuntimeException{
    public ConflictException(String message){
        super(message);
    }
}
