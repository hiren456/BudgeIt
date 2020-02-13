package com.codemonkeys9.budgeit.logiclayer.ParameterConverter;

public class ParameterConverterFactory {
    public static ParameterConverter createParameterConverter(){
        return new DefaultParameterConverter();
    }
}
