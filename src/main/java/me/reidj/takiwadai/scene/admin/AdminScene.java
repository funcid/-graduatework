package me.reidj.takiwadai.scene.admin;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.reidj.takiwadai.application.StatusType;
import me.reidj.takiwadai.exception.Exceptions;
import me.reidj.takiwadai.scene.AbstractScene;
import me.reidj.takiwadai.scene.log.LogScene;
import me.reidj.takiwadai.util.DbUtil;
import me.reidj.takiwadai.util.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

public class AdminScene extends AbstractScene {

    @FXML
    private TextArea logOutput;

    @FXML
    private TextField idApplication;

    @FXML
    private JFXComboBox<String> statuses;

    public AdminScene(Stage stage) {
        super("/fxml/admin/adminScene.fxml", stage);
    }

    public AdminScene() {
    }

    @FXML
    public void initialize() {
        fillingLogs();
        statuses.setValue(StatusType.IN_WORK.getTitle());
        statuses.getItems().addAll(Arrays.stream(StatusType.values()).map(StatusType::getTitle).toList());
    }

    private void fillingLogs() {
        StringBuilder fieldContent = new StringBuilder().append("****************************************\n");
        try (Connection connection = DbUtil.getDataSource().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DbUtil.SELECT_APPLICATIONS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                fieldContent.append("СОЗДАЛ:")
                        .append("\n")
                        .append(resultSet.getString("name"))
                        .append(" ")
                        .append(resultSet.getString("surname"))
                        .append(" ")
                        .append(resultSet.getString("secondName"))
                        .append(LogScene.SPLIT);
                fieldContent.append("Id заявки:")
                        .append("\n")
                        .append(resultSet.getString("id"))
                        .append(LogScene.SPLIT);
                LogScene.logText(resultSet, fieldContent, LogScene.SPLIT);
            }
            logOutput.setText(fieldContent.toString());
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onRemoveApplication() {
        String idApplicationText = idCheck();
        if (idApplicationText == null) {
            return;
        }
        try (Connection connection = DbUtil.getDataSource().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DbUtil.DELETE_APPLICATION);
            preparedStatement.setInt(1, Integer.parseInt(idApplicationText));
            preparedStatement.executeUpdate();
            fineAlert("Заявка удалена успешно!", "");
            fillingLogs();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void onUpdateStatus() {
        String idApplicationText = idCheck();
        if (idApplicationText == null) {
            return;
        }
        try (Connection connection = DbUtil.getDataSource().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DbUtil.UPDATE_APPLICATION_STATUS);
            preparedStatement.setString(1, statuses.getValue());
            preparedStatement.setInt(2, Integer.parseInt(idApplicationText));
            preparedStatement.executeUpdate();
            fineAlert("Статус заявки успешно обновлён!", "");
            fillingLogs();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    private String idCheck() {
        String idApplicationText = idApplication.getText();
        if (Exceptions.fieldIsEmpty.check(idApplicationText)) {
            Exceptions.fieldIsEmpty.alert();
            return null;
        } else if (!Utils.isNumber(idApplicationText)) {
            errorAlert("Неверный формат поля!", "");
            return null;
        }
        return idApplicationText;
    }
}
