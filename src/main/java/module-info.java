module com.example.javaprojectphase1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javaprojectphase1 to javafx.fxml;
    exports com.example.javaprojectphase1;
}