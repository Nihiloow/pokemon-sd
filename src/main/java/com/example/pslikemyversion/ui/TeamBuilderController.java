package com.example.pslikemyversion.ui;

import com.example.pslikemyversion.core.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class TeamBuilderController {

    @FXML private Button slot1, slot2, slot3, slot4, slot5, slot6;

    @FXML
    public void initialize() {
        // Pour le moment, on lie juste le clic des slots à la console
        slot1.setOnAction(e -> System.out.println("Slot 1 cliqué"));
        // Tu ajouteras la navigation vers PokemonDetail plus tard ici
    }

    @FXML
    private void onStartClicked(ActionEvent event) {
        // C'est cette méthode que le FXML cherchait !
        System.out.println("Lancement du combat...");
        SceneNavigator.switchScene((Node) event.getSource(), "battle-view.fxml");
    }
}