module com.example.vendadepassagens {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.jdbc;
    requires spring.tx;
    requires java.sql;
    requires org.mariadb.jdbc;


    opens com.vendadepassagens to javafx.fxml;
    exports com.vendadepassagens;
    exports com.vendadepassagens.dao;
    opens com.vendadepassagens.dao to javafx.fxml;
    exports com.config;
    opens com.config to javafx.fxml;
    exports com.vendadepassagens.controller;
    opens com.vendadepassagens.controller to javafx.fxml;
}