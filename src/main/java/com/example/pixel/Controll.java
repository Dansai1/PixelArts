package com.example.pixel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;

public class Controll {

    @FXML
    private Canvas canvas;

    private final int canvasSize = 512;
    private int gridSize = 16;
    private double cellSize;
    private Color colorDraw = Color.BLACK; //color por defecto

    @FXML
    public void initialize(){ //este evento con este nombre en especifico se carga al arrancar la app
        cellSize = (double) canvasSize / gridSize;
        drawGrid();

        //Evento: para el mause
        canvas.setOnMouseClicked(this::handleMouseClick);

    }

    private void drawGrid(){
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //Fondo blanco
        gc.setFill(Color.WHITE);
        gc.fillRect(0 ,0, canvasSize, canvasSize);

        //Lineas grises de la cuadricula
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(1);

        for (int i = 0; i <= gridSize; i++) {
            double pos = i * cellSize;
            gc.strokeLine(pos, 0, pos, canvasSize); // líneas verticales
            gc.strokeLine(0, pos, canvasSize, pos); // líneas horizontales
        }
    }

    //evento mause
    private void handleMouseClick(MouseEvent e) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        int col = (int) (e.getX() / cellSize);
        int row = (int) (e.getY() / cellSize);

        // Calcula coordenadas reales del cuadrado a pintar
        double x = col * cellSize;
        double y = row * cellSize;

        if (e.getButton() == MouseButton.PRIMARY) {
            // Pinta la celda
            gc.setFill(colorDraw);
            gc.fillRect(x, y, cellSize, cellSize);
        } else if (e.getButton() == MouseButton.SECONDARY) {
            gc.setFill(Color.WHITE);
            gc.fillRect(x, y, cellSize, cellSize);
        }

        // Redibuja la línea del borde para mantener la cuadrícula visible
        gc.setStroke(Color.LIGHTGRAY);
        gc.strokeRect(x, y, cellSize, cellSize);
    }

    //evetosColores
    @FXML
    private void processColor(ActionEvent e){
        Button boton = (Button) e.getSource();
        String colorStr = (String) boton.getUserData();
        this.colorDraw = Color.valueOf(colorStr);
    }

    @FXML
    private void processVista(ActionEvent e){
        MenuItem opcion = (MenuItem) e.getSource();
        int size = Integer.parseInt((String) opcion.getUserData());
        this.gridSize = size;

        cellSize = (double) canvasSize / gridSize;
        drawGrid();
    }
}
