package com.example.pixel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;//holi
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Escenario Principal
        stage.setTitle("PixelsArts");
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.setMaximized(true);

        FXMLLoader cargar = new FXMLLoader(getClass().getResource("index.fxml"));
        BorderPane pane = cargar. <BorderPane>load();

        Scene scene = new Scene(pane);
        scene.setCursor(Cursor.HAND);
        scene.getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}