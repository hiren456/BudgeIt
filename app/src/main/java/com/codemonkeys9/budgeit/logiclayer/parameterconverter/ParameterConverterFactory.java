package com.codemonkeys9.budgeit.logiclayer.parameterconverter;

public class ParameterConverterFactory {
    public static ParameterConverter createParameterConverter(){
        return new DefaultParameterConverter();
    }
}
