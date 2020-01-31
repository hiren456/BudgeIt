package LogicLayer.EntryCreator;


import Database.Database;

public class EntryCreatorFactory {
    public EntryCreator createEntryCreator(Database database){
        return new DefaultEntryCreator(database);
    }
}
