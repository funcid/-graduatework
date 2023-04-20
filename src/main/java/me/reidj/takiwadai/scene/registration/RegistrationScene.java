package me.reidj.takiwadai.scene.registration;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.reidj.takiwadai.App;
import me.reidj.takiwadai.database.DbUtil;
import me.reidj.takiwadai.exception.Exceptions;
import me.reidj.takiwadai.scene.AbstractScene;
import me.reidj.takiwadai.user.RoleType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class RegistrationScene extends AbstractScene {

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private TextField secondName;

    @FXML
    private TextField email;

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    private TextField surname;

    private Timer timer;

    public RegistrationScene() {

    }

    public RegistrationScene(Stage stage) {
        super("/fxml/registration/registrationScene.fxml", stage);
        try (Connection connection = DbUtil.getDataSource().getConnection()) {
            Statement st = connection.createStatement();
            st.executeUpdate(DbUtil.CREATE_TABLE);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void processRegistration() {
        String nameText = name.getText();
        String surnameText = surname.getText();
        String secondNameText = secondName.getText();
        String emailText = email.getText();
        String passwordText = password.getText();
        String confirmPasswordText = confirmPassword.getText();

        if (Exceptions.emailIsIncorrect.check(emailText)) {
            Exceptions.emailIsIncorrect.alert();
            return;
        } else if (Exceptions.fieldIsEmpty.check(nameText, surnameText, secondNameText, emailText, passwordText, confirmPasswordText)) {
            Exceptions.fieldIsEmpty.alert();
            return;
        } else if (Exceptions.passwordShort.check(passwordText)) {
            Exceptions.passwordShort.alert();
            return;
        } else if (Exceptions.symbolIsIncorrect.check(nameText, surnameText, secondNameText)) {
            Exceptions.symbolIsIncorrect.alert();
            return;
        } else if (Exceptions.passwordIsNotEqual.check(passwordText, confirmPasswordText)) {
            Exceptions.passwordIsNotEqual.alert();
            return;
        }

        try (Connection connection = DbUtil.getDataSource().getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(DbUtil.DUPLICATE_USER + "'" + email.getText() + "'");

            int resultCount = 0;
            while (resultSet.next()) {
                resultCount = resultSet.getInt(1);
                if (resultCount > 0) {
                    errorAlert("Пользователь с таким электронным адресом уже существует!", "");
                    break;
                }
            }

            if (resultCount == 0) {
                PreparedStatement prepareStatement = connection.prepareStatement(DbUtil.CREATE_USER);
                prepareStatement.setString(1, UUID.randomUUID().toString());
                prepareStatement.setString(2, nameText);
                prepareStatement.setString(3, surnameText);
                prepareStatement.setString(4, secondNameText);
                prepareStatement.setString(5, emailText);
                prepareStatement.setString(6, passwordText);
                prepareStatement.setString(7, RoleType.USER.name());
                prepareStatement.execute();
                fineAlert("Через 3 секунды Вы будете автоматически перенаправлены на страницу авторизации.", "Регистрация прошла успешно!");
                timer = new Timer();
                timer.schedule(new RedirectTask(), 3000);
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void move() throws IOException {
        App.getApp().getMainScene().getScene().setRoot(App.getApp().getMainScene().getParent());
    }

    class RedirectTask extends TimerTask {
        @Override
        public void run() {
            try {
                move();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            timer.cancel();
        }
    }
}
