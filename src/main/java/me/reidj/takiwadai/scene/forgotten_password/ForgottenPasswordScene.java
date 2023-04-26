package me.reidj.takiwadai.scene.forgotten_password;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.reidj.takiwadai.exception.Exceptions;
import me.reidj.takiwadai.scene.AbstractScene;
import me.reidj.takiwadai.service.MailSender;
import me.reidj.takiwadai.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ForgottenPasswordScene extends AbstractScene {

    @FXML
    private TextField email;

    public ForgottenPasswordScene(Stage stage) {
        super("/fxml/forgotten_password/forgottenPasswordScene.fxml", stage);
    }

    public ForgottenPasswordScene() {
    }

    @FXML
    void recoveryProcess() {
        String emailText = email.getText();
        if (Exceptions.fieldIsEmpty.check(emailText)) {
            Exceptions.fieldIsEmpty.alert();
            return;
        } else if (Exceptions.emailIsIncorrect.check(emailText)) {
            Exceptions.emailIsIncorrect.alert();
            return;
        }

        try (Connection connection = DbUtil.getDataSource().getConnection()) {
            PreparedStatement selectUser = connection.prepareStatement(DbUtil.SELECT_USER_BY_EMAIL);

            selectUser.setString(1, emailText);

            ResultSet resultSet = selectUser.executeQuery();
            if (resultSet.next()) {
                String newPassword = new MailSender().send(emailText, resultSet.getString("name"));

                PreparedStatement updatePassword = connection.prepareStatement(DbUtil.UPDATE_USER_PASSWORD);

                updatePassword.setString(1, newPassword);
                updatePassword.setString(2, emailText);

                updatePassword.execute();

                fineAlert("Письмо с новым паролем отправлено на адрес " + emailText, "");
            } else {
                errorAlert("Введённый вами почтовый адрес не найден в нашей системе!", "");
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }

    }
}
