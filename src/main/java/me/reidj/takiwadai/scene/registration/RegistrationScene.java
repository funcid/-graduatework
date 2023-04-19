package me.reidj.takiwadai.scene.registration;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.reidj.takiwadai.App;
import me.reidj.takiwadai.database.DbUtil;
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

import static me.reidj.takiwadai.exception.ExceptionType.*;

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
        super("/fxml/registration/registration.fxml", stage);
        try (Connection connection = DbUtil.getDataSource().getConnection()) {
            Statement st = connection.createStatement();
            st.executeUpdate(DbUtil.CREATE_TABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void processRegistration() {
        if (errorCheck(surname.getText(), name.getText(), secondName.getText(), email.getText(), password.getText(), confirmPassword.getText())) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean errorCheck(String surnameText, String nameText, String secondNameText, String emailText, String passwordText, String passwordConfirmText) {
        if (FIELD_IS_EMPTY.getPredicate().test(new String[]{
                secondNameText, passwordText, nameText, emailText, passwordConfirmText, secondNameText
        })) {
            errorAlert(FIELD_IS_EMPTY.getContextText(), FIELD_IS_EMPTY.getHeaderText());
            return true;
        } else if (SYMBOL_IS_INCORRECT.getPredicate().test(new String[]{
                surnameText, nameText, secondNameText
        })) {
            errorAlert(SYMBOL_IS_INCORRECT.getContextText(), SYMBOL_IS_INCORRECT.getHeaderText());
            return true;
        } else if (EMAIL_IS_INCORRECT.getPredicate().test(new String[]{emailText})) {
            errorAlert(EMAIL_IS_INCORRECT.getContextText(), EMAIL_IS_INCORRECT.getHeaderText());
            return true;
        } else if (PASSWORD_IS_NOT_EQUAL.getPredicate().test(new String[]{passwordText, passwordConfirmText})) {
            errorAlert(PASSWORD_IS_NOT_EQUAL.getContextText(), PASSWORD_IS_NOT_EQUAL.getHeaderText());
            return true;
        } else if (PASSWORD_SHORT.getPredicate().test(new String[]{passwordText})) {
            errorAlert(PASSWORD_SHORT.getContextText(), PASSWORD_SHORT.getHeaderText());
            return true;
        }
        return false;
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
