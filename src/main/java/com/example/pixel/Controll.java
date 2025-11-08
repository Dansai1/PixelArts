package com.example.pixel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;

public class Controll {

    @FXML
    private Canvas canvas;
    @FXML
    private Slider zoom;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private StackPane canvasContainer;

    private final int canvasSize = 512;
    private int gridSize = 16;
    private double cellSize;
    private Color colorDraw = Color.BLACK; //color por defecto

    @FXML
    public void initialize(){ //este evento con este nombre en especifico se carga al arrancar la app
        cellSize = (double) canvasSize / gridSize;
        drawGrid();
        zoom.setValue(100);

        // Centrar el canvas dentro del StackPane y asegurar pannable
        scrollPane.setPannable(true);
        canvasContainer.setAlignment(Pos.CENTER);

        updateCanvasContainerSize();

        // Listener del viewport para ajustar el tamaño del container y mantener centrado cuando sea más pequeño que el viewport
        scrollPane.viewportBoundsProperty().addListener((obs, oldB, newB) -> {
            updateCanvasContainerSize();
        });

        //zoom control
        zoom.valueProperty().addListener((obs, oldVal, newVal) ->{
            double scale = newVal.doubleValue() / 100;
            canvas.setScaleX(scale);
            canvas.setScaleY(scale);
            updateCanvasContainerSize();
        });

        //Evento: para el mause
        canvas.setOnMouseClicked(this::handleMouseClick);

    }

    private void updateCanvasContainerSize() {
        double scale = canvas.getScaleX(); // asumimos scaleX == scaleY
        double visualW = canvas.getWidth() * scale;
        double visualH = canvas.getHeight() * scale;

        double viewportW = scrollPane.getViewportBounds().getWidth();
        double viewportH = scrollPane.getViewportBounds().getHeight();

        double prefW = Math.max(visualW, (viewportW <= 0 ? visualW : viewportW));
        double prefH = Math.max(visualH, (viewportH <= 0 ? visualH : viewportH));

        canvasContainer.setPrefWidth(prefW);
        canvasContainer.setPrefHeight(prefH);
        canvasContainer.setMinWidth(prefW);
        canvasContainer.setMinHeight(prefH);

        // Forzamos relayout para que el ScrollPane actualice barras y posicionamiento
        canvasContainer.requestLayout();
        scrollPane.requestLayout();
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

        double scale = canvas.getScaleX(); // si escalas el canvas, los eventos están en coordenadas visuales
        double logicalX = e.getX() / (scale == 0 ? 1 : scale);
        double logicalY = e.getY() / (scale == 0 ? 1 : scale);

        int col = (int) (e.getX() / cellSize);
        int row = (int) (e.getY() / cellSize);

        // Validar índices
        if (col < 0 || row < 0 || col >= gridSize || row >= gridSize) return;

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
