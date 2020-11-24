package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {
    TextField textField_id = new TextField();
    TextField textField_title = new TextField();
    TextField textField_author = new TextField();
    TextField textField_publisher = new TextField();
    TextField textField_price = new TextField();
    Button button_first = new Button("第一");
    Button button_next = new Button("下一");
    Button button_former = new Button("前一");
    Button button_end = new Button("最后");
    Button button_insert = new Button("插入");
    Button button_delete = new Button("删除");
    Button button_update = new Button("修改");

    @Override
    public void start(Stage primaryStage) throws SQLException, DaoException {
        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.add(new Label("书号："), 0, 0);
        root.add(textField_id, 1, 0);
        root.add(new Label("书名："), 2, 0);
        root.add(textField_title, 3, 0);
        root.add(new Label("作者："), 0, 1);
        root.add(textField_author, 1, 1);
        root.add(new Label("出版社："), 2, 1);
        root.add(textField_publisher, 3, 1);
        root.add(new Label("价格："), 0, 2);
        root.add(textField_price, 1, 2);
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(button_first, button_former, button_next, button_end, button_insert, button_delete, button_update);
        root.add(hBox, 0, 4, 4, 1);
//        初始化
        BookDaoImpl bookDao = new BookDaoImpl();
        bookDao.rst.first();
        System.out.println(bookDao.rst.getInt(1) + "");
        textField_id.setText(bookDao.rst.getInt(1) + "");
        textField_title.setText(bookDao.rst.getString(2));
        textField_author.setText(bookDao.rst.getString(3));
        textField_publisher.setText(bookDao.rst.getString(4));
        textField_price.setText(bookDao.rst.getInt(5) + "");
//        按钮事件
        button_first.setOnAction(e -> {
            try {
                bookDao.rst.first();
                bookDao.reset(textField_id, textField_title, textField_author, textField_publisher, textField_price);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        button_former.setOnAction(e -> {
            try {
                if (bookDao.rst.previous()) {
                    bookDao.reset(textField_id, textField_title, textField_author, textField_publisher, textField_price);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.titleProperty().set("提示");
                    alert.headerTextProperty().set("当前已是首项，请重试！");
                    alert.showAndWait();
                    bookDao.rst.next();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        button_next.setOnAction(e -> {
            try {
                if (bookDao.rst.next()) {
                    bookDao.reset(textField_id, textField_title, textField_author, textField_publisher, textField_price);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.titleProperty().set("提示");
                    alert.headerTextProperty().set("当前已是尾项，请重试！");
                    alert.showAndWait();
                    bookDao.rst.previous();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        button_end.setOnAction(e -> {
            try {
                bookDao.rst.last();
                bookDao.reset(textField_id, textField_title, textField_author, textField_publisher, textField_price);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        button_insert.setOnAction(e -> bookDao.addBook(textField_id, textField_title, textField_author, textField_publisher, textField_price));
        button_update.setOnAction(e -> {
            try {
                bookDao.updateBookInfo(textField_id, textField_title, textField_author, textField_publisher, textField_price);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.titleProperty().set("提示");
                alert.headerTextProperty().set("修改信息成功！");
                alert.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        button_delete.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.titleProperty().set("提示");
            alert.headerTextProperty().set("你确认要删除吗？");
//            对用户选择做出反应动作
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        bookDao.rst.deleteRow();
                        if (bookDao.rst.previous()) {
                            bookDao.rst.next();
                            bookDao.rst.next();
                        }
                        bookDao.reset(textField_id, textField_title, textField_author, textField_publisher, textField_price);
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.titleProperty().set("提示");
                        alert1.headerTextProperty().set("删除成功！");
                        alert1.showAndWait();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
        });

        primaryStage.setTitle("Books");
        primaryStage.setResizable(false);
        Scene scene = new Scene(root, 450, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
