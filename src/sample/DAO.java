package sample;

import java.sql.*;

public interface DAO {
    //    String driver = "com.mysql.jdbc.Driver";
//     以下“******”部分更改为自己的数据库信息
    String url = "jdbc:mysql://localhost:3306/******?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    String user = "******";
    String password = "******";

    default Connection getConnection() throws DaoException {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DaoException();
        }
    }
}
