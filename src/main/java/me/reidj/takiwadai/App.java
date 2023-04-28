package me.reidj.takiwadai;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.reidj.takiwadai.config.FileManager;
import me.reidj.takiwadai.exception.Exceptions;
import me.reidj.takiwadai.scene.log.realization.AdminScene;
import me.reidj.takiwadai.scene.application.ApplicationScene;
import me.reidj.takiwadai.scene.forgotten_password.ForgottenPasswordScene;
import me.reidj.takiwadai.scene.log.LogScene;
import me.reidj.takiwadai.scene.login.LoginScene;
import me.reidj.takiwadai.scene.profile.ProfileScene;
import me.reidj.takiwadai.scene.registration.RegistrationScene;
import me.reidj.takiwadai.user.User;

import java.io.IOException;

public class App extends Application {

    private static App app;

    private LoginScene loginScene;

    private RegistrationScene registrationScene;

    private ApplicationScene applicationScene;

    private LogScene logScene;
    private AdminScene adminScene;
    private ProfileScene profileScene;
    private ForgottenPasswordScene forgottenPasswordScene;

    private FileManager settingsManager;

    private User user;

    @Override
    public void start(Stage stage) throws IOException {
        app = this;

        this.loginScene = new LoginScene(stage);
        this.registrationScene = new RegistrationScene(stage);
        this.applicationScene = new ApplicationScene(stage);
        this.logScene = new LogScene(stage);
        this.adminScene = new AdminScene(stage);
        this.profileScene = new ProfileScene(stage);
        this.forgottenPasswordScene = new ForgottenPasswordScene(stage);

        this.settingsManager = new FileManager();
        this.settingsManager.createFile();

        new Exceptions().init();

        stage.setScene(new Scene(loginScene.getParent()));
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

    public LoginScene getLoginScene() {
        return loginScene;
    }

    public RegistrationScene getRegistrationScene() {
        return registrationScene;
    }

    public LogScene getLogScene() {
        return logScene;
    }


    public AdminScene getAdminScene() {
        return adminScene;
    }

    public FileManager getSettingsManager() {
        return settingsManager;
    }

    public ProfileScene getProfileScene() {
        return profileScene;
    }

    public ForgottenPasswordScene getForgottenPasswordScene() {
        return forgottenPasswordScene;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}