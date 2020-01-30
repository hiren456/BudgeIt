package LogicLayer.EntryFetcher;

public class EntryFetcherFactory {
    public EntryFetcher createEntryFetcher(){
        return new DefaultEntryFetcher();
    }
}
