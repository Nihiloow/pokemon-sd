package com.example.pslikemyversion.ui;

import com.example.pslikemyversion.core.GameSession;
import com.example.pslikemyversion.core.SceneNavigator;
import com.example.pslikemyversion.logic.passive.PassiveFactory;
import com.example.pslikemyversion.logic.pokemons.Pokedex;
import com.example.pslikemyversion.logic.pokemons.Pokemon;
import com.example.pslikemyversion.logic.types.Type;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PokemonDetailController {

    private int currentSlot;

    @FXML private Label placeholderLabel;
    @FXML private ComboBox<String> pokemonNameSelector;
    @FXML private ComboBox<String> abilitySelector;
    @FXML private ComboBox<String> itemSelector;
    @FXML private ComboBox<String> move1, move2, move3, move4;

    @FXML
    public void initialize() {
        // Chargement initial des espèces et objets depuis la DB [cite: 360]
        pokemonNameSelector.getItems().setAll(Pokedex.getAvailableSpecies());
        itemSelector.getItems().setAll(Pokedex.getAllItems());

        // Événement : Filtrer les talents et attaques selon le Pokémon choisi
        pokemonNameSelector.setOnAction(e -> updateAvailableOptions(pokemonNameSelector.getValue()));
    }

    private void updateAvailableOptions(String name) {
        if (name == null) return;
        abilitySelector.getItems().setAll(Pokedex.getAbilitiesForPokemon(name));
        List<String> moves = Pokedex.getMovesForPokemon(name);
        for (ComboBox<String> cb : Arrays.asList(move1, move2, move3, move4)) {
            cb.getItems().setAll(moves);
        }
    }

    public void setPokemonSlot(int slotIndex) {
        this.currentSlot = slotIndex;
        placeholderLabel.setText("Editing Slot #" + slotIndex);

        Pokemon existing = GameSession.getInstance().getPokemon(slotIndex);
        if (existing != null) {
            pokemonNameSelector.setValue(existing.getName());
            updateAvailableOptions(existing.getName()); // Charge les listes filtrées

            // RECHARGE LES CHOIX PRÉCÉDENTS
            if (existing.getAbility() != null) abilitySelector.setValue(existing.getAbility().getName());
            if (existing.getHeldItem() != null) itemSelector.setValue(existing.getHeldItem().getName());

            // Recharge les attaques (ex: première attaque)
            if (!existing.getMoveSet().isEmpty()) move1.setValue(existing.getMoveSet().get(0).getName());
        }
    }

    @FXML
    private void onBackClicked(ActionEvent event) {
        String name = pokemonNameSelector.getValue();
        if (name != null) {
            saveSelectedPokemon(name);
        }
        SceneNavigator.switchScene((Node) event.getSource(), "teambuild-view.fxml");
    }

    private void saveSelectedPokemon(String name) {
        // 1. Création de l'objet de base avec les stats DB (sp_attack corrigé)
        Pokemon p = Pokedex.getPokemonByName(name);
        if (p == null) return;

        // 2. Mise à jour des types (Correction ArrayList : clear + addAll)
        p.getTypes().clear();
        p.getTypes().addAll(Pokedex.getTypesFor(name));

        // 3. Attribution du talent et de l'objet (Polymorphisme via Factory) [cite: 344, 365]
        if (abilitySelector.getValue() != null)
            p.setAbility(PassiveFactory.createAbility(abilitySelector.getValue()));
        if (itemSelector.getValue() != null)
            p.setHeldItem(PassiveFactory.createItem(itemSelector.getValue()));

        // 4. Attribution des 4 attaques choisies [cite: 41, 65]
        saveMoves(p);

        // 5. Sauvegarde dans la Team de la GameSession [cite: 62]
        GameSession.getInstance().savePokemon(currentSlot, p);
    }

    private void saveMoves(Pokemon p) {
        List<ComboBox<String>> boxes = Arrays.asList(move1, move2, move3, move4);
        p.getMoveSet().clear();
        for (ComboBox<String> cb : boxes) {
            if (cb.getValue() != null) {
                p.getMoveSet().add(Pokedex.getMoveByName(cb.getValue()));
            }
        }
    }

}