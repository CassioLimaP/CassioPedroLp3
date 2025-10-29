module com.example.vendadepassagens {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.vendadepassagens to javafx.fxml;
    exports com.example.vendadepassagens;
}