module com.example.cs230 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cs230 to javafx.fxml;
    exports com.example.cs230;
}