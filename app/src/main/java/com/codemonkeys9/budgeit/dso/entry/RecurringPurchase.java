package com.codemonkeys9.budgeit.dso.entry;

public interface RecurringPurchase extends RecurringEntry, BasePurchase {
    @Override
    RecurringPurchase flag();
    @Override
    RecurringPurchase unflag();
}
