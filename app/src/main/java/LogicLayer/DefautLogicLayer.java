package LogicLayer;

import java.util.Date;
import java.util.List;

import Entry.Entry;

class DefaultLogicLayer implements LogicLayer {
    // TODO: add method
    // TODO: add test
    @Override
    public List<Entry> fetchAllIncomeEntrys(Date startDate, Date endDate) {
        return null;
    }

    // TODO: add method
    // TODO: add test
    @Override
    public List<Entry> fetchAllPurchaseEntrys(Date startDate, Date endDate) {
        return null;
    }

    // TODO: add method
    // TODO: add test
    @Override
    public List<Entry> fetchAllEntrys(Date startDate, Date endDate) {
        return null;
    }

    // TODO: add method
    // TODO: add test
    @Override
    public int calculateTotalIncome(Date startDate, Date endDate) {
        return 0;
    }

    // TODO: add method
    // TODO: add test
    @Override
    public int calculateTotalPurchases(Date startDate, Date endDate) {
        return 0;
    }

    // TODO: add method
    // TODO: add test
    @Override
    public void createEntry(String amount, String category, String details, String date) {

    }
}
