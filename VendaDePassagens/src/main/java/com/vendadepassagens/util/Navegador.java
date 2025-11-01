package com.vendadepassagens.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Navegador {

    // Guarda a "janela" (Stage) principal da aplicação
    private static Stage stagePrincipal;
    private static Scene cenaLogin; // Guarda a cena de login para reuso

    /**
     * Define o Stage (janela) principal que o navegador vai controlar.
     */
    public static void setStagePrincipal(Stage stage) {
        stagePrincipal = stage;
    }

    /**
     * Define a cena de login original para podermos voltar a ela.
     */
    public static void setCenaLogin(Scene scene) {
        cenaLogin = scene;
    }

    /**
     * Retorna para a tela de login original.
     */
    public static void voltarParaLogin() {
        if (stagePrincipal != null && cenaLogin != null) {
            stagePrincipal.setScene(cenaLogin);
            stagePrincipal.setTitle("Login - Sistema de Passagens");
        }
    }

    /**
     * Carrega e exibe uma nova tela (FXML) na janela principal.
     * @param fxmlArquivo O caminho para o arquivo FXML (ex: "view/TelaCadastro.fxml")
     * @param titulo O novo título da janela
     */
    public static void mudarTela(String fxmlArquivo, String titulo) {
        try {
            // Carrega o FXML da pasta 'resources'
            String caminhoAbsoluto = "/" + fxmlArquivo;
            FXMLLoader fxmlLoader = new FXMLLoader(Navegador.class.getResource(caminhoAbsoluto));
            Parent root = fxmlLoader.load();

            // Cria uma nova cena com o FXML carregado
            Scene novaCena = new Scene(root);

            // Define a nova cena na janela principal
            stagePrincipal.setScene(novaCena);
            stagePrincipal.setTitle(titulo);

        } catch (IOException e) {
            System.err.println("Erro ao carregar o FXML: " + fxmlArquivo);
            e.printStackTrace();
        }
    }
}