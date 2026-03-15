package com.example.pslikemyversion.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class SceneNavigator {
    public static FXMLLoader switchScene(Node node, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneNavigator.class.getResource("/com/example/pokemonsdlike/" + fxmlFile));
            Scene scene = new Scene(loader.load(), 900, 600);
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(scene);
            return loader;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
