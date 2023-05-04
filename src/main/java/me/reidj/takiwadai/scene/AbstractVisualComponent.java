package me.reidj.takiwadai.scene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import lombok.NoArgsConstructor;
import me.reidj.takiwadai.App;

import java.io.IOException;

@NoArgsConstructor
public abstract class AbstractVisualComponent {

    private String fxmlPath;

    protected synchronized Parent getFxmlRoot() throws IOException {
        return new FXMLLoader(getClass().getResource(fxmlPath)).load();
    }

    public AbstractVisualComponent(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public void errorAlert(String contextText, String headerText) {
        Alert alert = new Alert(Alert.AlertType.ERROR, contextText);
        alert.setTitle("Ошибка!");
        alert.setHeaderText(headerText);
        alert.show();
    }

    public void fineAlert(String contextText, String headerText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, contextText);
        alert.setTitle("Успешно!");
        alert.setHeaderText(headerText);
        alert.show();
    }

    @FXML
    protected void backLoginScreen() throws IOException {
        App.getApp().getLoginScene().getScene().setRoot(App.getApp().getLoginScene().getParent());
    }

    @FXML
    void exit() {
        System.exit(0);
    }
}
