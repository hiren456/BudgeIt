package com.codemonkeys9.budgeit.logiclayer.dateparser;

public class DateParserFactory {
    public static DateParser createDateParser(){
        return new DefaultDateParser();
    }

}
