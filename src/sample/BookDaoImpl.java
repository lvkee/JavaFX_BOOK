package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.*;

public class BookDaoImpl implements BookDao {
    Connection conn = getConnection();
    Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    ResultSet rst = stmt.executeQuery("SELECT * FROM books");

    public BookDaoImpl() throws DaoException, SQLException {
    }

    @Override
    public void reset(TextField textField_id, TextField textField_title, TextField textField_author, TextField textField_publisher, TextField textField_price) throws SQLException {
        textField_id.setText(rst.getInt(1) + "");
        textField_title.setText(rst.getString(2));
        textField_author.setText(rst.getString(3));
        textField_publisher.setText(rst.getString(4));
        textField_price.setText(rst.getInt(5) + "");
    }

    @Override
    public void addBook(TextField textField_id, TextField textField_title, TextField textField_author, TextField textField_publisher, TextField textField_price) {
        try {
            rst.moveToInsertRow();
            int bookId = Integer.parseInt(textField_id.getText());
            String bookTitle = textField_title.getText();
            String bookAuthor = textField_author.getText();
            String bookPublisher = textField_publisher.getText();
            int bookPrice = Integer.parseInt(textField_price.getText());
            rst.updateInt(1, bookId);
            rst.updateString(2, bookTitle);
            rst.updateString(3, bookAuthor);
            rst.updateString(4, bookPublisher);
            rst.updateInt(5, bookPrice);
            rst.insertRow();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("提示");
            alert.headerTextProperty().set("添加成功！");
            alert.show();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.titleProperty().set("提示");
            alert.headerTextProperty().set("书号已存在，请检查后重新输入！");
            alert.showAndWait();
        }
    }

    @Override
    public void updateBookInfo(TextField textField_id, TextField textField_title, TextField textField_author, TextField textField_publisher, TextField textField_price) {
        try {
            int bookId = Integer.parseInt(textField_id.getText());
            String bookTitle = textField_title.getText();
            String bookAuthor = textField_author.getText();
            String bookPublisher = textField_publisher.getText();
            int bookPrice = Integer.parseInt(textField_price.getText());
            rst.updateInt(1, bookId);
            rst.updateString(2, bookTitle);
            rst.updateString(3, bookAuthor);
            rst.updateString(4, bookPublisher);
            rst.updateInt(5, bookPrice);
            rst.updateRow();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
