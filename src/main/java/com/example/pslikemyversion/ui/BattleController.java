package com.example.pslikemyversion.ui;

import com.example.pslikemyversion.core.GameSession;
import com.example.pslikemyversion.logic.pokemons.Pokemon;
import com.example.pslikemyversion.logic.types.Type;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import java.util.ArrayList;

public class BattleController {
    @FXML private TextArea battleLog;
    @FXML private ProgressBar playerHpBar, opponentHpBar;
    @FXML private Label playerName, opponentName;
    @FXML private Button move1, move2, move3, move4;

    private Pokemon playerActive;
    private Pokemon opponentActive;

    @FXML
    public void initialize() {
        // 1. On récupère ton Pokémon
        playerActive = GameSession.getInstance().getPokemon(1);

        // 2. On crée un adversaire de test (ex: un Salamèche adverse)
        opponentActive = new Pokemon("Salamèche Ennemi", new ArrayList<>(), null, 100, 100, 100, 100, 100, 100);

        if (playerActive != null) {
            playerName.setText(playerActive.getName());
            opponentName.setText(opponentActive.getName());

            // On affiche l'état initial des barres
            updateHpBar(playerHpBar, playerActive);
            updateHpBar(opponentHpBar, opponentActive);

            // On lie le premier bouton
            move1.setText("Charge");
            move1.setOnAction(e -> handlePlayerTurn("Charge"));
        }
    }

    private void handlePlayerTurn(String moveName) {
        // --- TOUR DU JOUEUR ---
        battleLog.appendText("> " + playerActive.getName() + " utilise " + moveName + " !\n");

        // On inflige des dégâts fixes pour le test
        opponentActive.takeDamages(15, "PHYSICAL", null);
        updateHpBar(opponentHpBar, opponentActive);

        // Désactiver les boutons pour éviter que le joueur spamme pendant la riposte
        setButtonsDisable(true);

        if (opponentActive.getHp().getStat() <= 0) {
            battleLog.appendText("L'adversaire est KO ! Victoire !\n");
        } else {
            // --- ATTENTE DE 1.5 SECONDE PUIS RIPOSTE ---
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(event -> handleOpponentTurn());
            pause.play();
        }
    }

    private void handleOpponentTurn() {
        // --- TOUR DE L'ADVERSAIRE ---
        battleLog.appendText("> L'adversaire riposte avec Charge !\n");

        playerActive.takeDamages(10, "PHYSICAL", null);
        updateHpBar(playerHpBar, playerActive);

        if (playerActive.getHp().getStat() <= 0) {
            battleLog.appendText(playerActive.getName() + " est KO... Défaite.\n");
        } else {
            // On réactive les boutons pour le tour suivant du joueur
            setButtonsDisable(false);
        }
    }

    private void updateHpBar(ProgressBar bar, Pokemon p) {
        double ratio = (double) p.getHp().getStat() / p.getMaxHp();
        bar.setProgress(ratio);

        // Couleur dynamique
        if (ratio < 0.2) bar.setStyle("-fx-accent: red;");
        else if (ratio < 0.5) bar.setStyle("-fx-accent: orange;");
        else bar.setStyle("-fx-accent: #2ecc71;");
    }

    private void setButtonsDisable(boolean state) {
        move1.setDisable(state);
        move2.setDisable(state);
        move3.setDisable(state);
        move4.setDisable(state);
    }
}