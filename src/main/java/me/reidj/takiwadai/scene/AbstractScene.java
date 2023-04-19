package me.reidj.takiwadai.scene;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractScene extends AbstractVisualComponent {

    private String fxmlPath;

    private Stage stage;

    public AbstractScene(String fxmlPath, Stage stage) {
        super(fxmlPath);
        this.fxmlPath = fxmlPath;
        this.stage = stage;
        stage.setTitle("Takiwadai");
        stage.initStyle(StageStyle.UNDECORATED);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("images/logo.png");
        if (inputStream != null) {
            stage.getIcons().add(new Image(inputStream));
        }
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
