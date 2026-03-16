package com.example.pslikemyversion.ui;

import com.example.pslikemyversion.core.GameSession;
import com.example.pslikemyversion.core.SceneNavigator;
import com.example.pslikemyversion.logic.pokemons.Pokemon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class TeamBuilderController {

    @FXML private Button slot1, slot2, slot3, slot4, slot5, slot6;

    @FXML
    public void initialize() {
        refreshUI();

        slot1.setOnAction(e -> openPokemonSelection(1));
        slot2.setOnAction(e -> openPokemonSelection(2));
        slot3.setOnAction(e -> openPokemonSelection(3));
        slot4.setOnAction(e -> openPokemonSelection(4));
        slot5.setOnAction(e -> openPokemonSelection(5));
        slot6.setOnAction(e -> openPokemonSelection(6));
    }

    private void refreshUI() {
        Button[] buttons = {slot1, slot2, slot3, slot4, slot5, slot6};

        for (int i = 0; i < 6; i++) {
            Pokemon p = GameSession.getInstance().getPokemon(i + 1);

            if (p != null) {
                buttons[i].setText(p.getName());
                buttons[i].setStyle("-fx-background-color: #D8B4FE; -fx-background-radius: 15; -fx-border-color: black;");
            } else {
                buttons[i].setText("+");
            }
        }
    }

    private void openPokemonSelection(int slotIndex) {
        FXMLLoader loader = SceneNavigator.switchScene(slot1, "pokemon-detail-view.fxml");

        if (loader != null) {
            PokemonDetailController detailCtrl = loader.getController();

            detailCtrl.setPokemonSlot(slotIndex);
        }
    }

    @FXML
    private void onStartClicked(ActionEvent event) {
        SceneNavigator.switchScene((Node) event.getSource(), "battle-view.fxml");
    }
}