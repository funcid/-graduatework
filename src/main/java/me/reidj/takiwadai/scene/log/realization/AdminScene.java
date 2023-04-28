package me.reidj.takiwadai.scene.log.realization;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import me.reidj.takiwadai.application.StatusType;
import me.reidj.takiwadai.scene.AbstractScene;
import me.reidj.takiwadai.scene.log.Log;
import me.reidj.takiwadai.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

import static me.reidj.takiwadai.util.Utils.wrapText;

public class AdminScene extends AbstractScene {

    @FXML
    private TableColumn<Log, String> applicationId;

    @FXML
    private TableColumn<Log, String> category;

    @FXML
    private TableColumn<Log, String> creator;

    @FXML
    private TableColumn<Log, String> dateCreation;

    @FXML
    private TableColumn<Log, String> description;

    @FXML
    private TableView<Log> logs;

    @FXML
    private TableColumn<Log, String> status;

    @FXML
    private JFXComboBox<String> statuses;

    @FXML
    private Button removeApplicationButton;

    @FXML
    private Button updateStatusButton;

    public AdminScene(Stage stage) {
        super("/fxml/admin/adminScene.fxml", stage);
    }

    public AdminScene() {
    }

    @FXML
    public void initialize() {
        applicationId.setCellValueFactory(new PropertyValueFactory<>("id"));
        creator.setCellValueFactory(new PropertyValueFactory<>("creator"));
        dateCreation.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        statuses.setValue(StatusType.IN_WORK.getTitle());
        statuses.getItems().addAll(Arrays.stream(StatusType.values()).map(StatusType::getTitle).toList());

        fillingLogs();
        selectionColumn();
    }

    private void fillingLogs() {
        logs.getItems().clear();
        try (Connection connection = DbUtil.getDataSource().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DbUtil.SELECT_APPLICATIONS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                logs.getItems().add(new Log(
                        resultSet.getInt("id"),
                        resultSet.getString("name") + " " + resultSet.getString("surname") + " " + resultSet.getString("secondName"),
                        resultSet.getString("date"),
                        resultSet.getString("category"),
                        wrapText(resultSet.getString("description")),
                        resultSet.getString("status")
                ));
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    private void selectionColumn() {
        logs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            int id = newValue.getId();
            removeApplication(id);
            updateStatus(id);
        });
    }

    private void removeApplication(int idApplication) {
        removeApplicationButton.setOnMouseClicked(event -> {
            try (Connection connection = DbUtil.getDataSource().getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(DbUtil.DELETE_APPLICATION);
                preparedStatement.setInt(1, idApplication);
                preparedStatement.executeUpdate();
                fineAlert("Заявка удалена успешно!", "");
                fillingLogs();
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void updateStatus(int idApplication) {
        updateStatusButton.setOnMouseClicked(event -> {
            try (Connection connection = DbUtil.getDataSource().getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(DbUtil.UPDATE_APPLICATION_STATUS);
                preparedStatement.setString(1, statuses.getValue());
                preparedStatement.setInt(2, idApplication);
                preparedStatement.executeUpdate();
                fineAlert("Статус заявки успешно обновлён!", "");
                fillingLogs();
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        });
    }
}
