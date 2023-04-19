package me.reidj.takiwadai.scene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public abstract class AbstractVisualComponent {

    private String fxmlPath;

    public AbstractVisualComponent() {
    }

    public AbstractVisualComponent(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    protected synchronized Parent getFxmlRoot() throws IOException {
        return new FXMLLoader(getClass().getResource(fxmlPath)).load();
    }

    @FXML
    void exit() {
        System.exit(0);
    }
}
