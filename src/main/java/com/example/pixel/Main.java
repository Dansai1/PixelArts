package com.example.pixel;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;//holi

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Escenario Principal
        stage.setTitle("PixelsArts");
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.setMaximized(true);

        Label label = new Label("Probando");
        Scene scene = new Scene(label, 400, 200);
        scene.setCursor(Cursor.HAND);
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}