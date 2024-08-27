package siit.config;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseManagerTest {

    @Test
    public void verifyDbConnection() throws SQLException {
        Connection connection = DataBaseManager.getPostgreSqlDataSourceConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT 1+1");
        rs.next();
        Integer dbValue = rs.getInt(1);
        assertEquals(2, dbValue);
    }


}