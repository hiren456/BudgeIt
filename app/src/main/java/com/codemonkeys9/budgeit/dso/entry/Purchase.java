package com.codemonkeys9.budgeit.dso.entry;

public interface Purchase extends Entry, BasePurchase {
    @Override
    Purchase flag();
    @Override
    Purchase unflag();
}
