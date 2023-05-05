package me.reidj.takiwadai.scene;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractScene extends AbstractVisualComponent {

    private Stage stage;

    public AbstractScene(String fxmlPath, Stage stage) {
        super(fxmlPath);
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

    public Scene getScene() {
        return getStage().getScene();
    }

    public Parent getParent() throws IOException {
        return getFxmlRoot();
    }

    public Stage getStage() {
        return stage;
    }
}
