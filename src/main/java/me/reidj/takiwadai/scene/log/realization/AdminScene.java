package me.reidj.takiwadai.scene.log.realization;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.val;
import me.reidj.takiwadai.App;
import me.reidj.takiwadai.application.StatusType;
import me.reidj.takiwadai.scene.AbstractScene;
import me.reidj.takiwadai.scene.log.Log;
import me.reidj.takiwadai.util.DbUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static me.reidj.takiwadai.util.Utils.wrapText;

@NoArgsConstructor
public class AdminScene extends AbstractScene {

    private static final String FILE_NAME = "Отчёт от ";

    private static final String LINE = "--------------------------------------";

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");

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
        try (val resultSet = getAllApplications()) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ResultSet getAllApplications() throws SQLException {
        Connection connection = DbUtil.getDataSource().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DbUtil.SELECT_APPLICATIONS);
        return preparedStatement.executeQuery();
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

    private final StringBuilder stringBuilder = new StringBuilder();

    @FXML
    private void createWordDocument() throws IOException {
        App.getApp().getFileManager().createFile(FILE_NAME + dateTimeFormatter.format(LocalDateTime.now()) + ".doc");
        try (val resultSet = getAllApplications()) {
            while (resultSet.next()) {
                stringBuilder
                        .append("Отчёт по присланным заявкам о технических неисправностях.")
                        .append("\n")
                        .append(LINE)
                        .append("\n")
                        .append("Код заявки: ")
                        .append(resultSet.getInt("id"))
                        .append("\n")
                        .append("Имя: ")
                        .append(resultSet.getString("name"))
                        .append("\n")
                        .append("Фамилия: ")
                        .append(resultSet.getString("surname"))
                        .append("\n")
                        .append("Отчество: ")
                        .append(resultSet.getString("secondName"))
                        .append("\n")
                        .append("Дата создания: ")
                        .append(resultSet.getString("date"))
                        .append("\n")
                        .append("Категория: ")
                        .append(resultSet.getString("category"))
                        .append("\n")
                        .append("Описание: ")
                        .append(wrapText(resultSet.getString("description")))
                        .append("\n")
                        .append("Статус: ")
                        .append(resultSet.getString("status"))
                        .append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        App.getApp().getFileManager().onWrite(stringBuilder.toString().getBytes());
    }
}
