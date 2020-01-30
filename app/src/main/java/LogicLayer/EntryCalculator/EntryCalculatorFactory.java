package LogicLayer.EntryCalculator;

import java.util.List;

import Entry.Entry;

class EntryCalculatorFactory {

    public EntryCalculator createEntryCaluculor(){
        return new DefaultEntryCalculator();
    }
}
