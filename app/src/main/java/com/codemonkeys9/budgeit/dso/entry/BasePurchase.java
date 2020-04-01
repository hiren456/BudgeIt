package com.codemonkeys9.budgeit.dso.entry;

public interface BasePurchase extends BaseEntry {
    boolean flagged();
    BasePurchase flag();
    BasePurchase unflag();
}
