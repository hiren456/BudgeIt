package com.codemonkeys9.budgeit.LogicLayer.DateParser;

public class DateParserFactory {
    public DateParser createDateParser(){
        return new DefaultDateParser();
    }

}
