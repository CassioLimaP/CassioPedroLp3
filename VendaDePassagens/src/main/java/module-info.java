// O nome do seu módulo (baseado no seu log)
module com.example.vendadepassagens {

    // --- Módulos que seu código precisa ---
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics; // Adicione este (para o StyleManager)
    requires spring.jdbc;
    requires spring.tx;
    requires java.sql;
    requires org.mariadb.jdbc;


    // --- PACOTES QUE SEU MÓDULO ABRE ---

    // 1. Abre seus pacotes de CÓDIGO para o JavaFX (para @FXML)
    opens com.vendadepassagens.controller to javafx.fxml;
    opens com.vendadepassagens.model to javafx.base;
    opens com.vendadepassagens.util to javafx.fxml;
    opens com.vendadepassagens to javafx.fxml, javafx.graphics;
    opens com.config to javafx.fxml; // (Você tinha este)
    opens com.vendadepassagens.dao to javafx.fxml; // (Você tinha este)


    // 2. ABRE SEUS PACOTES DE RECURSOS (A CORREÇÃO)

    // Abre a pasta "view" (para FXML e CSS)
    // Agora damos permissão ao FXML e ao Graphics (para o CSS)
    opens com.vendadepassagens.fxml to javafx.fxml, javafx.graphics;
    opens com.vendadepassagens.css;
    // Abre a pasta "imagens" (para o Navegador.java carregar)
    // O 'opens' simples abre para todos os módulos
    opens com.vendadepassagens.imagens;


    // (Opcional, mas bom ter)
    exports com.vendadepassagens;
}