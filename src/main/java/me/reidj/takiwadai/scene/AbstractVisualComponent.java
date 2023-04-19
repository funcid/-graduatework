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
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        return loader.load();
    }

    @FXML
    void exit() {
        System.exit(0);
    }
}
