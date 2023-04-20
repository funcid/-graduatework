package me.reidj.takiwadai.scene.login;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import me.reidj.takiwadai.App;
import me.reidj.takiwadai.database.DbUtil;
import me.reidj.takiwadai.exception.Exceptions;
import me.reidj.takiwadai.scene.AbstractScene;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginScene extends AbstractScene {

    @FXML
    private Pane contentArea;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    public LoginScene(Stage stage) {
        super("/fxml/login/loginScene.fxml", stage);
    }

    public LoginScene() {

    }

    @FXML
    void onLoginProcess() {
        String emailText = email.getText();
        String passwordText = password.getText();

        if (Exceptions.fieldIsEmpty.check(emailText, passwordText)) {
            Exceptions.fieldIsEmpty.alert();
            return;
        } else if (Exceptions.emailIsIncorrect.check(emailText)) {
            Exceptions.emailIsIncorrect.alert();
            return;
        } else if (Exceptions.passwordShort.check(passwordText)) {
            Exceptions.passwordShort.alert();
            return;
        }

        try (Connection connection = DbUtil.getDataSource().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DbUtil.SELECT_USER);

            preparedStatement.setString(1, emailText);
            preparedStatement.setString(2, passwordText);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("ura");
            } else {
                System.out.println("fuck");
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openRegistration() throws IOException {
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(App.getApp().getRegistrationScene().getParent());
    }
}
