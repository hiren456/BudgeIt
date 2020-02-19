package com.codemonkeys9.budgeit.logiclayer.uicalculator;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.exceptions.InvalidDateException;
import com.codemonkeys9.budgeit.exceptions.InvalidDateIntervalException;

public interface UICalculator {

    /*
    Calculates the sum total of all Income entered from
    startDate to endDate.
    startDate and endDate should be in "yyyy-mm-dd" format.
    If they are not in this format or the date does not exist, ie.
    "2000-02-30", then an InvalidDateException will get throw.
    "2000-2-20" would be an invalid date because the month does not
    have two digits.
    If the dates are valid but the startDate comes after, not on or before,
    the endDate then an InvalidDateIntervalException is thrown.
    The sum total is returned as an Amount object.
     */
    Amount calculateTotalIncome(String startDate, String  endDate)
        throws InvalidDateException, InvalidDateIntervalException;

    /*
    Calculates the sum total of all Income entered from
    "1970-01-01 to the current date.
    The sum total is returned as an Amount object.
     */
    Amount calculateTotalIncome();

    /*
    Calculates the sum total of all Purchase entered from
    startDate to endDate.
    startDate and endDate should be in "yyyy-mm-dd" format.
    If they are not in this format or the date does not exist, ie.
    "2000-02-30", then an InvalidDateException will get throw.
    "2000-2-20" would be an invalid date because the month does not
    have two digits.
    If the dates are valid but the startDate comes after, not on or before,
    the endDate then an InvalidDateIntervalException is thrown.
    The sum total is returned as an Amount object.
     */
    Amount calculateTotalPurchases(String startDate, String endDate)
        throws InvalidDateException, InvalidDateIntervalException;

    /*
    Calculates the sum total of all Purchases entered from
    "1970-01-01 to the current date.
    The sum total is returned as an Amount object.
     */
    Amount calculateTotalPurchases();

    /*
    Calculates the sum total of all Entries entered from
    startDate to endDate.
    startDate and endDate should be in "yyyy-mm-dd" format.
    If they are not in this format or the date does not exist, ie.
    "2000-02-30", then an InvalidDateException will get throw.
    "2000-2-20" would be an invalid date because the month does not
    have two digits.
    If the dates are valid but the startDate comes after, not on or before,
    the endDate then an InvalidDateIntervalException is thrown.
    The sum total is returned as an Amount object.
     */
    Amount calculateTotal(String startDate, String endDate)
        throws InvalidDateException, InvalidDateIntervalException;

    /*
    Calculates the sum total of all Entries entered from
    "1970-01-01 to the current date.
    The sum total is returned as an Amount object.
     */
    Amount calculateTotal();
}
