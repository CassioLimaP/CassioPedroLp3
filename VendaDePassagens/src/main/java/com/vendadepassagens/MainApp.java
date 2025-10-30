package com.vendadepassagens;

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
        // (Vamos criar este arquivo a seguir)
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/TelaLogin.fxml"));
        Parent root = fxmlLoader.load();

        // 2. Cria a cena e a exibe
        Scene scene = new Scene(root, 400, 300); // Define o tamanho
        primaryStage.setTitle("Login - Sistema de Passagens");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static void main(String[] args) {
        launch(args);
    }
}
