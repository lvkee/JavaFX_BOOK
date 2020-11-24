package sample;

import javafx.scene.control.TextField;

import java.sql.SQLException;

public interface BookDao extends DAO {

    void reset(TextField textField_id, TextField textField_title, TextField textField_author, TextField textField_publisher, TextField textField_price) throws DaoException, SQLException;

    void addBook(TextField textField_id, TextField textField_title, TextField textField_author, TextField textField_publisher, TextField textField_price) throws DaoException;

    void updateBookInfo(TextField textField_id, TextField textField_title, TextField textField_author, TextField textField_publisher, TextField textField_price) throws DaoException;

}
