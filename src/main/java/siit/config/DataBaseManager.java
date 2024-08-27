package siit.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseManager {

    public static Connection getPostgreSqlDataSourceConnection() throws SQLException {
        //config ca sa va conectati la Postgre
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "Geamana191990";
        return DriverManager.getConnection(url, user, password);

    }


}
