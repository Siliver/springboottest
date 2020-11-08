package com.siliver.ch1.util;


import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.stereotype.Component;

@Component
public class SimpleFunction implements Function {

    public Object call(Object[] pars, Context context){
        return "hi";
    }

}
