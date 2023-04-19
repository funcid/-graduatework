package me.reidj.takiwadai.scene;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public abstract class AbstractScene extends AbstractVisualComponent {

    private String fxmlPath;

    private Stage stage;

    public AbstractScene(String fxmlPath, Stage stage) {
        super(fxmlPath);
        this.fxmlPath = fxmlPath;
        this.stage = stage;
        stage.setTitle("Takiwadai");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
    }

    public AbstractScene() {

    }

    public String getFxmlPath() {
        return fxmlPath;
    }

    public Stage getStage() {
        return stage;
    }

    public void setFxmlPath(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        return getStage().getScene();
    }

    public Parent getParent() throws IOException {
        return getFxmlRoot();
    }
}
