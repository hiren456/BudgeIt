package Database;

public class DatabaseFactory {
    public Database createDatabase(){
        return new StubDatabase();
    }
}
