package me.reidj.takiwadai.scene.main;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import me.reidj.takiwadai.App;
import me.reidj.takiwadai.scene.AbstractScene;

import java.io.IOException;

public class MainScene extends AbstractScene {

    @FXML
    private Pane contentArea;

    public MainScene(Stage stage) {
        super("/fxml/main/mainWindow.fxml", stage);
    }

    public MainScene() {

    }

    @FXML
    void openRegistration() throws IOException {
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(App.getApp().getRegistrationScene().getParent());
    }
}
