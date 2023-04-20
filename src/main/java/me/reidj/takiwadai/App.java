package me.reidj.takiwadai;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.reidj.takiwadai.exception.Exceptions;
import me.reidj.takiwadai.scene.application.ApplicationScene;
import me.reidj.takiwadai.scene.login.LoginScene;
import me.reidj.takiwadai.scene.registration.RegistrationScene;
import me.reidj.takiwadai.user.User;

import java.io.IOException;

public class App extends Application {

    private static App app;

    private LoginScene mainScene;

    private RegistrationScene registrationScene;

    private ApplicationScene applicationScene;

    private User user;

    @Override
    public void start(Stage stage) throws IOException {
        app = this;

        this.mainScene = new LoginScene(stage);
        this.registrationScene = new RegistrationScene(stage);
        this.applicationScene = new ApplicationScene(stage);

        new Exceptions().init();

        stage.setScene(new Scene(mainScene.getParent()));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static App getApp() {
        return app;
    }

    public ApplicationScene getApplicationScene() {
        return applicationScene;
    }

    public LoginScene getMainScene() {
        return mainScene;
    }

    public RegistrationScene getRegistrationScene() {
        return registrationScene;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}