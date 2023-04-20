module me.reidj.takiwadai {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.zaxxer.hikari;
    requires com.google.protobuf;
    requires java.sql;
    requires com.jfoenix;

    opens me.reidj.takiwadai to javafx.fxml;
    exports me.reidj.takiwadai;
    exports me.reidj.takiwadai.scene;
    exports me.reidj.takiwadai.user;
    opens me.reidj.takiwadai.scene to javafx.fxml;
    exports me.reidj.takiwadai.scene.login;
    exports me.reidj.takiwadai.scene.registration;
    exports me.reidj.takiwadai.scene.application;
    opens me.reidj.takiwadai.scene.login to javafx.fxml;
    opens me.reidj.takiwadai.scene.registration to javafx.fxml;
    opens me.reidj.takiwadai.scene.application to javafx.fxml;
    exports me.reidj.takiwadai.exception;
    opens me.reidj.takiwadai.exception to javafx.fxml;
}