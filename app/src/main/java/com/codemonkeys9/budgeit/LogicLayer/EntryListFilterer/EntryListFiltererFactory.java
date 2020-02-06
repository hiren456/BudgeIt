package com.codemonkeys9.budgeit.LogicLayer.EntryListFilterer;

public class EntryListFiltererFactory {
    public EntryListFilterer creatEntryListFilterer(){
        return new DefaultEntryListFilterer();
    }
}
