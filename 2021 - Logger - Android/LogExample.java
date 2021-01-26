package com.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LogExample {

    @Getter
    private static final String TAG = "LogExample";

    private String className = "";

    private String methodName = "";

    private String message = "";
    
}
