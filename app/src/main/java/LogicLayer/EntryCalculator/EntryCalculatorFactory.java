package LogicLayer.EntryCalculator;

public class EntryCalculatorFactory {

    public EntryCalculator createEntryCalculator(){
        return new DefaultEntryCalculator();
    }
}
