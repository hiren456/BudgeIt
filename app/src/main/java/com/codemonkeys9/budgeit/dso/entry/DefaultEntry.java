package com.codemonkeys9.budgeit.dso.entry;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.details.Details;

abstract class DefaultEntry extends DefaultBaseEntry implements Entry {
    int entryID;

    DefaultEntry(Amount amount, int entryID, Details details, Date date, int catID) {
        super(amount, details, date, catID);
        this.entryID = entryID;
    }

    @Override
    public DefaultEntry modifyEntry(Amount amount, Details details, Date date) {
        return (DefaultEntry) super.modifyEntry(amount, details, date);
    }

    @Override
    public abstract DefaultEntry clone();

    @Override
    public DefaultEntry changeCategory(int catID) {
        return (DefaultEntry) super.changeCategory(catID);
    }

    @Override
    public int getEntryID() {
        return this.entryID;
    }

    @Override
    public boolean equals(Entry other) {
        return this.getEntryID() == other.getEntryID() && super.equals(other);
    }
}
