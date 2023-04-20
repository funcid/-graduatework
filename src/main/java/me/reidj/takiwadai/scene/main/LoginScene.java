package me.reidj.takiwadai.scene.main;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import me.reidj.takiwadai.App;
import me.reidj.takiwadai.scene.AbstractScene;

import java.io.IOException;

public class LoginScene extends AbstractScene {

    @FXML
    private Pane contentArea;

    public LoginScene(Stage stage) {
        super("/fxml/login/loginScene.fxml", stage);
    }

    public LoginScene() {

    }

    @FXML
    void openRegistration() throws IOException {
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(App.getApp().getRegistrationScene().getParent());
    }
}
