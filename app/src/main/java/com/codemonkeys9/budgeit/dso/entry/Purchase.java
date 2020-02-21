package com.codemonkeys9.budgeit.dso.entry;

public interface Purchase extends Entry {
    boolean flagged();
    Purchase flag();
    Purchase unflag();
}
