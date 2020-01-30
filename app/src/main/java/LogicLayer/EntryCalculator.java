package LogicLayer;

import java.util.List;

import Entry.Entry;

interface EntryCalculator {

    // Takes a list of entrys and sums
    // their amount
    int sumEntryList(List<Entry> entryList);

}
