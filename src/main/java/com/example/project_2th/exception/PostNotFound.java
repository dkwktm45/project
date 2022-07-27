package com.example.project_2th.exception;

public class PostNotFound extends SuperException {
    private static final String MESSAGE = "존재하지 않습니다.";

    public PostNotFound(){
        super(MESSAGE);
    }

    public PostNotFound(String name, String message){
        super(MESSAGE);
        addValidation(name,message);
    }

    @Override
    public int getStatusCode(){
        return 400;
    }
}
