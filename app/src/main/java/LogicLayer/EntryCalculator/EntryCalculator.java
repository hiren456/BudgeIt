package LogicLayer.EntryCalculator;

import java.util.List;

import Entry.Entry;

public interface EntryCalculator {

    // Takes a list of entrys and sums
    // their amount
    int sumEntryList(List<Entry> entryList);

}
