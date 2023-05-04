package me.reidj.takiwadai.scene.profile;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import me.reidj.takiwadai.App;
import me.reidj.takiwadai.exception.Exceptions;
import me.reidj.takiwadai.scene.AbstractScene;
import me.reidj.takiwadai.user.User;
import me.reidj.takiwadai.util.DbUtil;
import me.reidj.takiwadai.util.Utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@NoArgsConstructor
public class ProfileScene extends AbstractScene {

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Pane contentArea;

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    private TextField secondName;

    @FXML
    private TextField surname;

    public ProfileScene(Stage stage) {
        super("/fxml/profile/profileScene.fxml", stage);
    }

    @FXML
    private void initialize() {
        User user = App.getApp().getUser();
        name.setText(user.name());
        surname.setText(user.surname());
        secondName.setText(user.secondName());
        password.setText(user.password());
        confirmPassword.setText(user.password());
    }

    @FXML
    private void onUpdate() {
        String nameText = name.getText();
        String surnameText = surname.getText();
        String secondNameText = secondName.getText();
        String passwordText = password.getText();
        String confirmPasswordText = confirmPassword.getText();

        if (Exceptions.fieldIsEmpty.check(nameText, surnameText, secondNameText, passwordText, confirmPasswordText)) {
            Exceptions.fieldIsEmpty.alert();
            return;
        } else if (Exceptions.passwordIsNotEqual.check(passwordText, confirmPasswordText)) {
            Exceptions.passwordIsNotEqual.alert();
            return;
        } else if (Exceptions.symbolIsIncorrect.check(nameText, surnameText, secondNameText)) {
            Exceptions.symbolIsIncorrect.alert();
            return;
        } else if (Exceptions.passwordShort.check(passwordText)) {
            Exceptions.passwordShort.alert();
            return;
        }

        try (Connection connection = DbUtil.getDataSource().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DbUtil.UPDATE_USER);

            preparedStatement.setString(1, nameText);
            preparedStatement.setString(2, surnameText);
            preparedStatement.setString(3, secondNameText);
            preparedStatement.setString(4, passwordText);
            preparedStatement.setInt(5, App.getApp().getUser().id());

            preparedStatement.executeUpdate();

            fineAlert("Данные успешно обновлены.", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goApplications() throws IOException {
        openProfile();
    }

    private void openProfile() throws IOException {
        Utils.childrenRemove(contentArea.getChildren());
        contentArea.getChildren().setAll(App.getApp().getApplicationScene().getParent());
    }
}
