package com.vendadepassagens;

import com.vendadepassagens.util.Navegador;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        // 1. Aponta para o seu ARQUIVO FXML da tela de login
        // Nova Linha 17:
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/com/vendadepassagens/fxml/TelaLogin.fxml"));
        Parent root = fxmlLoader.load();

        // 2. Cria a cena e a exibe
        Scene sceneLogin = new Scene(root, 400, 300); // Define o tamanho

        Navegador.setStagePrincipal(primaryStage);
        Navegador.setCenaLogin(sceneLogin);

        primaryStage.setTitle("Login - Sistema de Passagens");
        primaryStage.setScene(sceneLogin);
        primaryStage.show();
    }

    static void main(String[] args) {
        launch(args);
    }
}
