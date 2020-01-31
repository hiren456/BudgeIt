package Entry;

import java.util.Date;

class DefaultEntry implements Entry {
    private int amount;
    private int entryID;
    //private int catID;
    private String details;
    private Date date;

    DefaultEntry(int amount, int entryID, String details, Date date){
        // TODO: ensure for valid parameters

        this.amount = amount;
        this.entryID = entryID;
        //this.catID = catID;
        this.details = details;
        this.date = date;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public int getEntryID() {
        return this.entryID;
    }

    //@Override
    //public int getCatID() {
    //    return this.catID;
    //}

    @Override
    public String getDetails() {
        return this.details;
    }

    @Override
    public Date getDate() {
        return this.date;
    }

    @Override
    public Entry modifyEntry(int amount, String details, Date date) {

        int newAmount = amount;
        int newEntryID = this.entryID;
        //int newCatID = catID;
        String newDetails = details;
        Date newDate = date;

        return new DefaultEntry(newAmount,newEntryID,newDetails,newDate);
    }
}
