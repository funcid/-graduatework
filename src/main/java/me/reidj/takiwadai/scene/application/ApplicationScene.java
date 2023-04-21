package me.reidj.takiwadai.scene.application;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import me.reidj.takiwadai.App;
import me.reidj.takiwadai.exception.Exceptions;
import me.reidj.takiwadai.scene.AbstractScene;
import me.reidj.takiwadai.user.User;
import me.reidj.takiwadai.util.DbUtil;

import java.io.IOException;
import java.sql.*;

public class ApplicationScene extends AbstractScene {

    @FXML
    private JFXComboBox<String> category;

    @FXML
    private TextArea description;

    @FXML
    private Pane contentArea;

    public ApplicationScene(Stage stage) {
        super("/fxml/application/applicationScene.fxml", stage);
        try (Connection connection = DbUtil.getDataSource().getConnection()) {
            Statement st = connection.createStatement();
            st.executeUpdate(DbUtil.CREATE_TABLE_APPLICATIONS);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        category.getItems().addAll("Сайт", "Техника", "Установка ПО");
    }

    public ApplicationScene() {
    }

    @FXML
    void sendProcess() {
        String descriptionText = description.getText();
        String categoryValue = category.getValue();

        if (Exceptions.fieldIsEmpty.check(descriptionText, categoryValue)) {
            Exceptions.fieldIsEmpty.alert();
            return;
        }

        User user = App.getApp().getUser();

        try (Connection connection = DbUtil.getDataSource().getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(DbUtil.CREATE_APPLICATION);
            prepareStatement.setInt(1, user.id());
            prepareStatement.setString(2, user.name());
            prepareStatement.setString(3, user.surname());
            prepareStatement.setString(4, user.secondName());
            prepareStatement.setString(5, user.email());
            prepareStatement.setString(6, descriptionText);
            prepareStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            prepareStatement.setString(8, categoryValue);
            prepareStatement.execute();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openLog() throws IOException {
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(App.getApp().getLogScene().getParent());
    }
}
