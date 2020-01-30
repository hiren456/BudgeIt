package LogicLayer.EntryFetcher;


class EntryFetcherFactory {
    public EntryFetcher createEntryFetcher(){
        return new DefaultEntryFetcher();
    }
}
