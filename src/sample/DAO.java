package sample;

import java.sql.*;

public interface DAO {
    //    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/exp5?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    String user = "root";
    String password = "12345678Aa.";

    default Connection getConnection() throws DaoException {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DaoException();
        }
    }
}
