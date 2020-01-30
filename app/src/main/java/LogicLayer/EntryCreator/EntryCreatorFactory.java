package LogicLayer.EntryCreator;


class EntryCreatorFactory {
    public EntryCreator createEntryCreator(){
        return new DefaultEntryCreator();
    }
}
