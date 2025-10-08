module com.example.pixel {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pixel to javafx.fxml;
    exports com.example.pixel;
}