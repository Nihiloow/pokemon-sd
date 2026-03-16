package com.example.pslikemyversion.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;

public class SceneNavigator {

    private static final String BASE_PATH = "/com/example/pslikemyversion/";

    public static FXMLLoader switchScene(Node node, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneNavigator.class.getResource(BASE_PATH + fxmlFile));

            Scene scene = new Scene(loader.load(), 900, 600);

            Stage stage = (Stage) node.getScene().getWindow();

            stage.setScene(scene);
            stage.centerOnScreen();

            return loader;

        } catch (IOException e) {
            System.err.println("ERREUR NAVIGATION : Impossible de charger " + fxmlFile);
            e.printStackTrace();
            return null;
        }
    }
}
