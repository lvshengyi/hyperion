package core.poseidon;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author LvShengyI
 */
public class jdbcTest {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hyperion_test";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    @Test
    public void jdbcTest() {
        try {
            Class.forName(JDBC_DRIVER);

            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM simple_test";

            ResultSet resultSet = stmt.executeQuery(sql);

            while(resultSet.next()){
                System.out.println(resultSet.getInt("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
