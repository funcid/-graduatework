package me.reidj.takiwadai.scene.log;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import me.reidj.takiwadai.App;
import me.reidj.takiwadai.scene.AbstractScene;
import me.reidj.takiwadai.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogScene extends AbstractScene {

    public static final String SPLIT = "\n-----------------------\n";

    @FXML
    private TextArea logOutput;

    public LogScene(Stage stage) {
        super("/fxml/log/logScene.fxml", stage);
    }

    public LogScene() {
    }

    public final StringBuilder fieldContent = new StringBuilder().append("****************************************\n");

    @FXML
    public void initialize() {
        try (Connection connection = DbUtil.getDataSource().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DbUtil.SELECT_APPLICATION);

            preparedStatement.setInt(1, App.getApp().getUser().id());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                logText(resultSet, fieldContent, SPLIT);
            }
            logOutput.setText(fieldContent.toString());
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    public static void logText(ResultSet resultSet, StringBuilder fieldContent, String split) throws SQLException {
        fieldContent.append("ДАТА СОЗДАНИЯ:")
                .append("\n")
                .append(resultSet.getString("date"))
                .append(split);
        fieldContent.append("КАТЕГОРИЯ:")
                .append("\n")
                .append(resultSet.getString("category"))
                .append(split);
        fieldContent.append("ОПИСАНИЕ:")
                .append("\n")
                .append(resultSet.getString("description"))
                .append(split);
        fieldContent.append("СТАТУС:")
                .append("\n")
                .append(resultSet.getString("status"))
                .append(" ")
                .append("\n****************************************\n");
    }
}
