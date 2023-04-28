package me.reidj.takiwadai.scene.login;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import me.reidj.takiwadai.App;
import me.reidj.takiwadai.exception.Exceptions;
import me.reidj.takiwadai.scene.AbstractScene;
import me.reidj.takiwadai.user.RoleType;
import me.reidj.takiwadai.user.User;
import me.reidj.takiwadai.util.DbUtil;
import me.reidj.takiwadai.util.Utils;

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

    @FXML
    private JFXCheckBox saveData;

    private final Gson gson = new Gson();

    public LoginScene(Stage stage) {
        super("/fxml/login/loginScene.fxml", stage);
    }

    public LoginScene() {

    }

    @FXML
    private void initialize() {
        String read = new String(App.getApp().getSettingsManager().onRead());
        String data = gson.fromJson(read, String.class);
        if (data != null) {
            String[] pair = data.split(":");
            email.setText(pair[0]);
            password.setText(pair[1]);
            saveData.fire();
        }
    }

    @FXML
    private void onLoginProcess() {
        String emailText = email.getText();
        String passwordText = password.getText();
        tryLogin(emailText, passwordText);
    }

    private void tryLogin(String email, String password) {
        if (Exceptions.fieldIsEmpty.check(email, password)) {
            Exceptions.fieldIsEmpty.alert();
            return;
        } else if (Exceptions.emailIsIncorrect.check(email)) {
            Exceptions.emailIsIncorrect.alert();
            return;
        } else if (Exceptions.passwordShort.check(password)) {
            Exceptions.passwordShort.alert();
            return;
        }

        try (Connection connection = DbUtil.getDataSource().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DbUtil.SELECT_USER_BY_EMAIL_AND_PASSWORD);

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("secondName"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        RoleType.valueOf(resultSet.getString("roleType"))
                );
                App.getApp().setUser(user);
                if (user.roleType() == RoleType.USER) {
                    openProfile();
                } else {
                    openAdminScene();
                }
            } else {
                errorAlert("Неверный логин или пароль!", "");
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSaveData() {
        String emailText = email.getText();
        String passwordText = password.getText();
        if (Exceptions.fieldIsEmpty.check(emailText, passwordText)) {
            return;
        }
        App.getApp().getSettingsManager().onWrite(gson.toJson(emailText + ":" + passwordText).getBytes());
    }

    @FXML
    private void onForgottenPassword() throws IOException {
        childrenRemove();
        contentArea.getChildren().setAll(App.getApp().getForgottenPasswordScene().getParent());
    }

    @FXML
    private void openRegistration() throws IOException {
        childrenRemove();
        contentArea.getChildren().setAll(App.getApp().getRegistrationScene().getParent());
    }

    private void openProfile() throws IOException {
        childrenRemove();
        contentArea.getChildren().setAll(App.getApp().getProfileScene().getParent());
    }

    private void openAdminScene() throws IOException {
        childrenRemove();
        contentArea.getChildren().setAll(App.getApp().getAdminScene().getParent());
    }

    private void childrenRemove() {
        Utils.childrenRemove(contentArea.getChildren());
    }
}
