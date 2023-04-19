package me.reidj.takiwadai.scene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import java.io.IOException;

public abstract class AbstractVisualComponent {

    private String fxmlPath;

    protected synchronized Parent getFxmlRoot() throws IOException {
        return new FXMLLoader(getClass().getResource(fxmlPath)).load();
    }

    public AbstractVisualComponent(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public AbstractVisualComponent() {

    }

    public void errorAlert(String contextText, String headerText) {
        Alert alert = new Alert(Alert.AlertType.ERROR, contextText);
        alert.setTitle("Ошибка!");
        alert.setHeaderText(headerText);
        alert.show();
    }

    @FXML
    void exit() {
        System.exit(0);
    }
}
