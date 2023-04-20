package me.reidj.takiwadai.scene.application;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import me.reidj.takiwadai.database.DbUtil;
import me.reidj.takiwadai.scene.AbstractScene;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.UUID;

public class ApplicationScene extends AbstractScene {

    @FXML
    private JFXComboBox<String> category;

    @FXML
    private TextArea description;

    public ApplicationScene(Stage stage) {
        super("/fxml/application/application.fxml", stage);
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
        try (Connection connection = DbUtil.getDataSource().getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(DbUtil.CREATE_APPLICATION);
            prepareStatement.setString(2, UUID.randomUUID().toString());
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void sendProcess() {
    }
}
