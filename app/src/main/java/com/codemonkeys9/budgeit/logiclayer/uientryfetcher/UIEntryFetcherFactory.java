package com.codemonkeys9.budgeit.logiclayer.uientryfetcher;

public class UIEntryFetcherFactory {
    public static UIEntryFetcher createUIEntryFetcher(){
        return new EntryFetcher();
    }
}
