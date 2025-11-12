module com.example.vendadepassagens {
    // Requires (sem mudanças)
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires spring.jdbc;
    requires spring.tx;
    requires java.sql;
    requires org.mariadb.jdbc;

    // --- ABRE PACOTES DE CÓDIGO ---
    opens com.vendadepassagens.controller to javafx.fxml;
    opens com.vendadepassagens.util to javafx.fxml;
    opens com.vendadepassagens.model to javafx.base;
    opens com.vendadepassagens to javafx.fxml, javafx.graphics;
    // Abre o seu pacote de configuração real
    opens com.vendadepassagens.config to javafx.fxml;

    // --- ABRE PACOTES DE RECURSOS (A NOVA FORMA) ---
    // Agora isto é "legal" porque são pacotes Java válidos
    opens com.vendadepassagens.fxml;
    opens com.vendadepassagens.css;
    opens com.vendadepassagens.imagens;

    // Exports (sem mudanças)
    exports com.vendadepassagens;
}