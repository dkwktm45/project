package com.example.project_2th.adapter;

public class PostNotFound extends RuntimeException{
    private static final String MESSAGE = "존재하지 않습니다.";

    public PostNotFound(){
        super(MESSAGE);
    }
}
