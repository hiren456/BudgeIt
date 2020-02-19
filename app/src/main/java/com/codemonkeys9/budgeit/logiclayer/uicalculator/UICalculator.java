package com.codemonkeys9.budgeit.logiclayer.uicalculator;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.exceptions.InvalidDateException;
import com.codemonkeys9.budgeit.exceptions.InvalidDateIntervalException;

public interface UICalculator {
    // Calculates the sum total of all Income entered from
    // startDate and endDate should be in "dd/mm/yyyy" format
    // passing "now" for endDate will use the current date as the endDate
    // the startDate to the endDate
    // always returns a non negative number
    Amount calculateTotalIncome(String startDate, String  endDate)
        throws InvalidDateException, InvalidDateIntervalException;

    // Calculates the sum total of all Income entered from
    // the default date to the current date
    // always returns a non negative number
    Amount calculateTotalIncome();

    // Calculates the sum total of all Purchases entered from
    // startDate and endDate should be in "dd/mm/yyyy" format
    // passing "now" for endDate will use the current date as the endDate
    // the startDate to the endDate
    // always returns a non positive number
    Amount calculateTotalPurchases(String startDate, String endDate)
        throws InvalidDateException, InvalidDateIntervalException;

    // Calculates the sum total of all Purchases entered from
    // the default date to the current date
    // always returns a non positive number
    Amount calculateTotalPurchases();

    // Calculates the sum total of all Entrys entered from
    // startDate and endDate should be in "dd/mm/yyyy" format
    // passing "now" for endDate will use the current date as the endDate
    // the startDate to the endDate
    // always returns a non positive number
    Amount calculateTotal(String startDate, String endDate)
        throws InvalidDateException, InvalidDateIntervalException;

    // Calculates the sum total of all Entrys entered from
    // the default date to the current date
    // always returns a non positive number
    Amount calculateTotal();
}
