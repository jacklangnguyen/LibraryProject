package com.library.demo.utils.exception;

public class DataConflictException extends Exception {
    private final String[] fileds;

    public DataConflictException(String... fileds){
        super(String.format("Data in some fields have conflict: %s", String.join(",",fileds)));
        this.fileds = fileds;
    }

    public String[] getFileds(){
        return this.fileds;
    }
}
