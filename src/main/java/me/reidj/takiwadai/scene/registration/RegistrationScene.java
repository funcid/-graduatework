package me.reidj.takiwadai.scene.registration;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.reidj.takiwadai.App;
import me.reidj.takiwadai.scene.AbstractScene;

import java.io.IOException;

public class RegistrationScene extends AbstractScene {

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private PasswordField email;

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    private TextField surname;


    public RegistrationScene(Stage stage) {
        super("/fxml/registration/registration.fxml", stage);
    }

    public RegistrationScene() {
    }

    @FXML
    void processRegistration() {
    }

    @FXML
    void move() throws IOException {
        App.getApp().getMainScene().getScene().setRoot(App.getApp().getMainScene().getParent());
    }
}
