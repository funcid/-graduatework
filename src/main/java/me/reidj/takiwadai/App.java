package me.reidj.takiwadai;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.reidj.takiwadai.scene.main.MainScene;
import me.reidj.takiwadai.scene.registration.RegistrationScene;

import java.io.IOException;

public class App extends Application {

    private static App app;

    private MainScene mainScene;
    private RegistrationScene registrationScene;

    @Override
    public void start(Stage stage) throws IOException {
        app = this;

        this.mainScene = new MainScene(stage);
        this.registrationScene = new RegistrationScene(stage);

        stage.setScene(new Scene(mainScene.getParent()));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public RegistrationScene getRegistrationScene() {
        return registrationScene;
    }

    public static App getApp() {
        return app;
    }

    public MainScene getMainScene() {
        return mainScene;
    }
}