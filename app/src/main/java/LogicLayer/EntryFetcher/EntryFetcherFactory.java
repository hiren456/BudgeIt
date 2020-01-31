package LogicLayer.EntryFetcher;

import Database.Database;

public class EntryFetcherFactory {
    public EntryFetcher createEntryFetcher(Database database){
        return new DefaultEntryFetcher(database);
    }
}
