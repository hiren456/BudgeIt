package com.codemonkeys9.budgeit.logiclayer.dateparser;

public class DateParserFactory {
    public DateParser createDateParser(){
        return new DefaultDateParser();
    }

}
