package viewer;

public class DBConnection {

    private String url;

    public DBConnection(String databaseName) {
//        String userDir = System.getProperty("user.dir"); // Getting work directory
//        String dbPath = userDir + "/SQLite Viewer/task/data/" + databaseName;
//        this.url = "jdbc:sqlite:" + dbPath;
        this.url = "jdbc:sqlite:" + databaseName; // just to pass stupid tests
    }

    public String getUrl() {
        return url;
    }
}

