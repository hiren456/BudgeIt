package com.codemonkeys9.budgeit.logiclayer.ParameterConverter;

import java.util.Date;

public interface ParameterConverter {

    public String createDisplayAmount(int amount);

    public String createDisplayDate(Date date);

    public int parseDisplayAmount(String displayAmount);

    public Date parseDate(String date);
}
