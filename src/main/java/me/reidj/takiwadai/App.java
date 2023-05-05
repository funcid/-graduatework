package me.reidj.takiwadai;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import me.reidj.takiwadai.config.FileManager;
import me.reidj.takiwadai.scene.application.ApplicationScene;
import me.reidj.takiwadai.scene.forgotten_password.ForgottenPasswordScene;
import me.reidj.takiwadai.scene.log.LogScene;
import me.reidj.takiwadai.scene.log.realization.AdminScene;
import me.reidj.takiwadai.scene.login.LoginScene;
import me.reidj.takiwadai.scene.profile.ProfileScene;
import me.reidj.takiwadai.scene.registration.RegistrationScene;
import me.reidj.takiwadai.user.User;

import java.io.IOException;

@Getter
public class App extends Application {

    @Getter
    private static App app;

    private LoginScene loginScene;

    private RegistrationScene registrationScene;

    private ApplicationScene applicationScene;

    private LogScene logScene;
    private AdminScene adminScene;
    private ProfileScene profileScene;
    private ForgottenPasswordScene forgottenPasswordScene;

    private FileManager fileManager;

    @Setter
    private User user;

    private static final String FILE_NAME = "settings.json";

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

        this.fileManager = new FileManager();
        this.fileManager.createFile(FILE_NAME);

        stage.setScene(new Scene(loginScene.getParent()));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}