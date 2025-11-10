package com.vendadepassagens.util;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Region;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

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
            //e.printStackTrace();
        }
    }
    public static void setBackgroundImage(Region region, String imagePath) {
        try {
            // 1. Carrega a imagem usando o ClassLoader (o método confiável)
            Image image = new Image(Objects.requireNonNull(Navegador.class.getResourceAsStream(imagePath)));

            // 2. Define o "cover"
            BackgroundSize bgSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                    false, false, true, true);

            // 3. Cria a imagem de fundo
            BackgroundImage bgImage = new BackgroundImage(
                    image,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    bgSize
            );

            // 4. Aplica o fundo à Região
            region.setBackground(new Background(bgImage));

        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem de fundo: " + imagePath);
            //e.printStackTrace(); // Opcional: mostrar o erro completo
        }
    }
}