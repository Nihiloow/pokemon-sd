package com.example.pslikemyversion.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher {
    public static void init(Stage stage) {
        try {
            // On commence par l'écran de démarrage (start-view.fxml)
            FXMLLoader loader = new FXMLLoader(AppLauncher.class.getResource("/com/example/pslikemyversion/start-view.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);
            stage.setTitle("Pokémon SD-Like - TeamBuilder");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
