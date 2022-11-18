package com.library.demo.utils.exception;

import lombok.Getter;

@Getter
public class DataNotFoundException extends Exception {
    private  final String dataKey;

    public DataNotFoundException() {this.dataKey = null;}

    public DataNotFoundException(String dataKey) {
        super(String.format("data '%s' is not found", dataKey));
        this.dataKey = dataKey;
    }
}
