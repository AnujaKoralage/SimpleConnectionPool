package lk.ijse.dep;

import java.sql.Connection;
import java.sql.SQLException;

public class AppInitializer {

    public static void main(String[] args) throws SQLException {
        ConnectionPool instance = ConnectionPool.getInstance();

        Connection connection1 = instance.getConnection();
        Connection connection2 = instance.getConnection();
        Connection connection3 = instance.getConnection();
        Connection connection4 = instance.getConnection();
        Connection connection5 = instance.getConnection();
        Connection connection6 = instance.getConnection();
        System.out.println(connection6);

        instance.releaseConnection(connection1);
        System.out.println(connection6);

    }

}
