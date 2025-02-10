package gifty.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import oracle.jdbc.OracleDriver;

public class DataBase {
    static Connection connection;

    private DataBase() throws SQLException {
        DriverManager.registerDriver(new OracleDriver());
        connection = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/freepdb1",
                "gifty", "gifty");
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null)
            new DataBase();
        return connection;
    }
}
