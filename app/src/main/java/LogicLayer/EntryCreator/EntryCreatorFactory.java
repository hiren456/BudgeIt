package LogicLayer.EntryCreator;


public class EntryCreatorFactory {
    public EntryCreator createEntryCreator(){
        return new DefaultEntryCreator();
    }
}
