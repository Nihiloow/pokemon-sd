package com.example.pslikemyversion.ui;

import com.example.pslikemyversion.core.GameSession;
import com.example.pslikemyversion.core.SceneNavigator;
import com.example.pslikemyversion.logic.pokemons.Pokedex;
import com.example.pslikemyversion.logic.pokemons.Pokemon;
import com.example.pslikemyversion.logic.types.Type;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import java.util.ArrayList;

public class PokemonDetailController {

    private int currentSlot;

    @FXML private Label placeholderLabel;
    @FXML private ComboBox<String> pokemonNameSelector;
    @FXML private ComboBox<String> abilitySelector;
    @FXML private ComboBox<String> itemSelector;
    @FXML private ComboBox<String> move1, move2, move3, move4;

    @FXML
    public void initialize() {
        pokemonNameSelector.getItems().addAll(Pokedex.getAvailableSpecies());
        abilitySelector.getItems().addAll(Pokedex.getAvailableAbilities());
        itemSelector.getItems().addAll(Pokedex.getAvailableItems());

        move1.getItems().addAll("Tackle", "Growl", "Ember", "Quick Attack");
        move2.getItems().addAll("Tackle", "Growl", "Ember", "Quick Attack");
        move3.getItems().addAll("Tackle", "Growl", "Ember", "Quick Attack");
        move4.getItems().addAll("Tackle", "Growl", "Ember", "Quick Attack");
    }

    public void setPokemonSlot(int slotIndex) {
        this.currentSlot = slotIndex;
        if (placeholderLabel != null) {
            placeholderLabel.setText("Editing Slot #" + slotIndex);
        }

        Pokemon existing = GameSession.getInstance().getPokemon(slotIndex);
        if (existing != null) {
            pokemonNameSelector.setValue(existing.getName());
        }
    }

    @FXML
    private void onBackClicked(ActionEvent event) {
        String name = pokemonNameSelector.getValue();

        if (name != null) {
            ArrayList<Type> types = Pokedex.getTypesFor(name);

            Pokemon p = new Pokemon(name, types, null, 100, 100, 100, 100, 100, 100);

            GameSession.getInstance().savePokemon(currentSlot, p);
        }

        SceneNavigator.switchScene((Node) event.getSource(), "teambuild-view.fxml");
    }
}