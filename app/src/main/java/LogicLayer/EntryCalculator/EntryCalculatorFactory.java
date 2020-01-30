package LogicLayer.EntryCalculator;

public class EntryCalculatorFactory {

    public EntryCalculator createEntryCaluculor(){
        return new DefaultEntryCalculator();
    }
}
