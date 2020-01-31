package Database;

public class DatabaseFactory {
    public Database createDatabase(int initialEntryID,int initialCatID){
        return new StubDatabase(initialCatID,initialCatID);
    }
}
