package LogicLayer;

import java.util.Date;
import java.util.List;

import Database.Database;
import Database.DatabaseFactory;
import Entry.Entry;
import LogicLayer.EntryCalculator.EntryCalculator;
import LogicLayer.EntryCalculator.EntryCalculatorFactory;
import LogicLayer.EntryCreator.EntryCreator;
import LogicLayer.EntryCreator.EntryCreatorFactory;
import LogicLayer.EntryFetcher.EntryFetcher;
import LogicLayer.EntryFetcher.EntryFetcherFactory;

class DefaultLogicLayer implements LogicLayer {

    private EntryFetcherFactory entryFetcherFactory;
    private EntryCalculatorFactory entryCalculatorFactory;
    private EntryCreatorFactory entryCreatorFactory;
    private EntryFetcher entryFetcher;
    private EntryCalculator entryCalculator;
    private EntryCreator entryCreator;
    private DatabaseFactory databaseFactory;
    private Database database;


    DefaultLogicLayer(){
        // Create factories for objects
        this.entryCalculatorFactory = new EntryCalculatorFactory();
        this.entryFetcherFactory = new EntryFetcherFactory();
        this.entryCreatorFactory = new EntryCreatorFactory();
        this.databaseFactory = new DatabaseFactory();

        // Create objects with factories
        this.database = this.databaseFactory.createDatabase(0);
        this.entryCreator = this.entryCreatorFactory.createEntryCreator(this.database);
        this.entryFetcher = this.entryFetcherFactory.createEntryFetcher(this.database);
        this.entryCalculator = this.entryCalculatorFactory.createEntryCalculator();

    }

    @Override
    public List<Entry> fetchAllIncomeEntrys(Date startDate, Date endDate) {
        // TODO: ensure valid start date and end date
        // TODO: test the validity of the returned list
        return this.entryFetcher.fetchAllIncomeEntrys(startDate, endDate);
    }

    @Override
    public List<Entry> fetchAllPurchaseEntrys(Date startDate, Date endDate) {
        // TODO: ensure valid start date and end date
        // TODO: test the validity of the returned list
        return this.entryFetcher.fetchAllPurchasesEntrys(startDate, endDate);
    }

    @Override
    public List<Entry> fetchAllEntrys(Date startDate, Date endDate) {
        // TODO: ensure valid start date and end date
        // TODO: test the validity of the returned list
        return this.entryFetcher.fetchAllEntrys(startDate,endDate);
    }

    @Override
    public int calculateTotalIncome(Date startDate, Date endDate) {
        // TODO: ensure valid start date and end date
        List<Entry> entryList = fetchAllIncomeEntrys(startDate,endDate);
        // TODO: ensure validity of entry list
        // TODO: test validity of sum
        return this.entryCalculator.sumEntryList(entryList);
    }

    @Override
    public int calculateTotalPurchases(Date startDate, Date endDate) {
        // TODO: ensure valid start date and end date
        List<Entry> entryList = fetchAllPurchaseEntrys(startDate,endDate);
        // TODO: ensure validity of entry list
        // TODO: test validity of sum
        return this.entryCalculator.sumEntryList(entryList);
    }

    @Override
    public void createEntry(String amount,  String details, String date) {
        // TODO: ensure validity of parameters
        // TODO: test for successful creation
        this.entryCreator.createEntry(amount,details,date);
    }
}
