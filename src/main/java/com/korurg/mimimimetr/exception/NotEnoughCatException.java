package com.korurg.mimimimetr.exception;

public class NotEnoughCatException extends Exception {
    public NotEnoughCatException(String errorMessage){
        super(errorMessage);
    }
    public NotEnoughCatException(){
        super("Not enough cat");
    }
}
