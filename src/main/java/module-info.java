module me.reidj.takiwadai {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.zaxxer.hikari;
    requires com.google.protobuf;
    requires java.sql;
    requires com.jfoenix;
    requires com.google.gson;

    opens me.reidj.takiwadai to javafx.fxml;
    exports me.reidj.takiwadai;
    exports me.reidj.takiwadai.scene;
    exports me.reidj.takiwadai.user;
    opens me.reidj.takiwadai.scene to javafx.fxml;
    exports me.reidj.takiwadai.scene.login;
    exports me.reidj.takiwadai.scene.registration;
    exports me.reidj.takiwadai.scene.application;
    exports me.reidj.takiwadai.scene.log;
    exports me.reidj.takiwadai.scene.admin;
    exports me.reidj.takiwadai.scene.profile;
    opens me.reidj.takiwadai.scene.login to javafx.fxml;
    opens me.reidj.takiwadai.scene.registration to javafx.fxml;
    opens me.reidj.takiwadai.scene.application to javafx.fxml;
    opens me.reidj.takiwadai.scene.log to javafx.fxml;
    opens me.reidj.takiwadai.scene.admin to javafx.fxml;
    opens me.reidj.takiwadai.scene.profile to javafx.fxml;
    exports me.reidj.takiwadai.exception;
    exports me.reidj.takiwadai.config;
    opens me.reidj.takiwadai.exception to javafx.fxml;
}