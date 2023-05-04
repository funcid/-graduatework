package me.reidj.takiwadai.scene.log;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import me.reidj.takiwadai.App;
import me.reidj.takiwadai.scene.AbstractScene;
import me.reidj.takiwadai.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static me.reidj.takiwadai.util.Utils.wrapText;

@NoArgsConstructor
public class LogScene extends AbstractScene {

    @FXML
    private TableColumn<Log, String> category;

    @FXML
    private TableColumn<Log, String> dateCreation;

    @FXML
    private TableColumn<Log, String> description;

    @FXML
    private TableView<Log> logs;

    @FXML
    private TableColumn<Log, String> status;

    public LogScene(Stage stage) {
        super("/fxml/log/logScene.fxml", stage);
    }

    @FXML
    private void initialize() {
        dateCreation.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        try (Connection connection = DbUtil.getDataSource().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DbUtil.SELECT_APPLICATION);

            preparedStatement.setInt(1, App.getApp().getUser().id());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                logs.getItems().add(new Log(
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
}
