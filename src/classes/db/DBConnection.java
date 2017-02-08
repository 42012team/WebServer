package classes.db;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    public static OracleDataSource ds;
    private static volatile DBConnection instance = null;
    private DBConnection() {
        try {
            ds = new OracleDataSource();
            ds.setDriverType("thin");
            ds.setURL("jdbc:oracle:thin:@Localhost:1521:mydb");
            ds.setDatabaseName("orcl");
            ds.setUser("hr");
            ds.setPassword("hr");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    public OracleDataSource getDataSourse() {
        synchronized (instance) {
            return ds;
        }
    }

}
