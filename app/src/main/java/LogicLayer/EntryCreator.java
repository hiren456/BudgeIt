package LogicLayer;

interface EntryCreator {

    // Creates and stores an entry
    // TODO: through exception if strings are invalid
    // TODO: Is category supposed to be a string, if so we will have to search the DB
    // TODO: for that category
    void createEntry(String amount, String category, String details,String date);
}