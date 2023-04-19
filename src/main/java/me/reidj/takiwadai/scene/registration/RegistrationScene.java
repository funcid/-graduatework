package me.reidj.takiwadai.scene.registration;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import me.reidj.takiwadai.App;
import me.reidj.takiwadai.scene.AbstractScene;

import java.io.IOException;

public class RegistrationScene extends AbstractScene {

    @FXML
    private TextField name;


    public RegistrationScene(Stage stage) {
        super("/fxml/registration/registration.fxml", stage);
    }

    public RegistrationScene() {

    }

    @FXML
    void processRegistration(MouseEvent event) {

    }

    @FXML
    void move() throws IOException {
        App.getApp().getMainScene().getStage().getScene().setRoot(App.getApp().getMainScene().getParent());
    }
}
