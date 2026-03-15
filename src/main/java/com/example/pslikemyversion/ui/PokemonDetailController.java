package com.example.pslikemyversion.ui;

import com.example.pslikemyversion.core.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

public class PokemonDetailController {
    @FXML
    private void onBackClicked(ActionEvent event) {
        // Retour au Teambuilder
        SceneNavigator.switchScene((Node) event.getSource(), "teambuild-view.fxml");
    }
}