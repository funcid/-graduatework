package me.reidj.takiwadai.scene.registration;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.reidj.takiwadai.App;
import me.reidj.takiwadai.database.DbUtil;
import me.reidj.takiwadai.exception.Exception;
import me.reidj.takiwadai.exception.*;
import me.reidj.takiwadai.scene.AbstractScene;
import me.reidj.takiwadai.user.RoleType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

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

    private final List<Exception> exceptions = new ArrayList<>(Arrays.asList(
            new FieldIsEmpty(),
            new SymbolIsIncorrect(),
            new EmailIsIncorrect(),
            new PasswordShort(),
            new PasswordIsNotEqual()
    ));

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
        Exception exception = exceptions.stream()
                .filter(exceptions -> exceptions.check(new String[]{
                        surname.getText(), name.getText(), secondName.getText(), email.getText(), password.getText(), confirmPassword.getText()
                })).findFirst().orElse(null);

        if (exception != null) {
            exception.alert();
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
                prepareStatement.setString(2, name.getText());
                prepareStatement.setString(3, surname.getText());
                prepareStatement.setString(4, secondName.getText());
                prepareStatement.setString(5, email.getText());
                prepareStatement.setString(6, password.getText());
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
