package com.codemonkeys9.budgeit.logiclayer.idmanager;

public class IDManagerFactory {
    public static IDManager createIDManager(){
        return new Incrementor();
    }
}
