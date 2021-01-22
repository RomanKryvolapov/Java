package com.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LogExample {

    private static final String TAG = "LogData";

    private String className = "";

    private String methodName = "";

    private String message = "";
    
}
