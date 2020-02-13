package com.codemonkeys9.budgeit.logiclayer.uicalculaterequesthandler;

public interface UICalculateRequestHandler {
    // Calculates the sum total of all Income entered from
    // startDate and endDate should be in "dd/mm/yyyy" format
    // passing "now" for endDate will use the current date as the endDate
    // the startDate to the endDate
    // always returns a non negative number
    String calculateTotalIncome(String startDate, String  endDate);

    // Calculates the sum total of all Income entered from
    // the default date to the current date
    // always returns a non negative number
    String calculateTotalIncome();

    // Calculates the sum total of all Purchases entered from
    // startDate and endDate should be in "dd/mm/yyyy" format
    // passing "now" for endDate will use the current date as the endDate
    // the startDate to the endDate
    // always returns a non positive number
    String calculateTotalPurchases(String startDate, String endDate);

    // Calculates the sum total of all Purchases entered from
    // the default date to the current date
    // always returns a non positive number
    String calculateTotalPurchases();

    // Calculates the sum total of all Entrys entered from
    // startDate and endDate should be in "dd/mm/yyyy" format
    // passing "now" for endDate will use the current date as the endDate
    // the startDate to the endDate
    // always returns a non positive number
    String calculateTotal(String startDate, String endDate);

    // Calculates the sum total of all Entrys entered from
    // the default date to the current date
    // always returns a non positive number
    String calculateTotal();
}
