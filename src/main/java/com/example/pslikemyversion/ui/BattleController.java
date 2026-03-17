package com.example.pslikemyversion.ui;

import com.example.pslikemyversion.core.GameSession;
import com.example.pslikemyversion.engine.CombatSystem;
import com.example.pslikemyversion.logic.moves.Move;
import com.example.pslikemyversion.logic.pokemons.Pokedex;
import com.example.pslikemyversion.logic.pokemons.Pokemon;
import com.example.pslikemyversion.logic.pokemons.Team;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import java.util.List;

public class BattleController {
    @FXML private TextArea battleLog;
    @FXML private ProgressBar playerHpBar, opponentHpBar;
    @FXML private Label playerName, opponentName;
    @FXML private Button move1, move2, move3, move4;
    @FXML private Button switchPoke1, switchPoke2;

    private Pokemon playerActive;
    private Pokemon opponentActive;
    private Team playerTeam;

    @FXML
    public void initialize() {
        // 1. Chargement de la vraie équipe du joueur
        playerTeam = GameSession.getInstance().getPlayerTeam();
        playerActive = playerTeam.getActivePokemon();

        // 2. Création d'un adversaire réel via la DB pour le test
        opponentActive = Pokedex.getPokemonByName("Baojian");
        if (opponentActive != null) {
            opponentActive.getTypes().addAll(Pokedex.getTypesFor("Baojian"));
            // On lui donne une attaque par défaut pour la riposte
            opponentActive.getMoveSet().add(Pokedex.getMoveByName("Chute de Glace"));
        }

        refreshUI();
        setupSwitchButtons();
    }

    private void refreshUI() {
        if (playerActive == null || opponentActive == null) return;

        playerName.setText(playerActive.getName());
        opponentName.setText(opponentActive.getName());

        updateHpBar(playerHpBar, playerActive);
        updateHpBar(opponentHpBar, opponentActive);

        // Liaison des boutons aux vraies attaques du MoveSet
        List<Move> moves = playerActive.getMoveSet();
        Button[] buttons = {move1, move2, move3, move4};

        for (int i = 0; i < 4; i++) {
            if (i < moves.size()) {
                Move m = moves.get(i);
                buttons[i].setText(m.getName());
                buttons[i].setDisable(false);
                buttons[i].setOnAction(e -> handleTurn(m));
            } else {
                buttons[i].setText("-");
                buttons[i].setDisable(true);
            }
        }
    }

    private void handleTurn(Move playerMove) {
        setButtonsDisable(true);

        // ETAPE 1 : Déterminer l'ordre selon la Vitesse
        boolean playerFirst = playerActive.getSpeed().getRealStat() >= opponentActive.getSpeed().getRealStat();

        if (playerFirst) {
            executeAttack(playerActive, opponentActive, playerMove);
            if (opponentActive.getHp().getStat() > 0) {
                pause(1.0, () -> executeOpponentAttack());
            }
        } else {
            executeOpponentAttack();
            if (playerActive.getHp().getStat() > 0) {
                pause(1.0, () -> executeAttack(playerActive, opponentActive, playerMove));
            }
        }
    }

    private void executeAttack(Pokemon attacker, Pokemon defender, Move move) {
        String result = CombatSystem.performAttack(attacker, defender, move);
        battleLog.appendText("> " + result + "\n");

        updateHpBar(playerHpBar, playerActive);
        updateHpBar(opponentHpBar, opponentActive);

        if (defender.getHp().getStat() <= 0) {
            battleLog.appendText("!!! " + defender.getName() + " est hors de combat !!!\n");
            checkBattleEnd(); // Cette méthode gère maintenant la réactivation partielle
        } else {
            // Personne n'est mort ? On redonne le contrôle total au joueur
            setButtonsDisable(false);
        }
    }

    private void executeOpponentAttack() {
        if (opponentActive.getMoveSet().isEmpty()) return;
        Move enemyMove = opponentActive.getMoveSet().get(0);
        executeAttack(opponentActive, playerActive, enemyMove);
    }

    @FXML
    private void onSwitchRequested(int index) {
        // 1. Vérification de l'existence du Pokémon dans la Team
        if (index < 0 || index >= playerTeam.getPokemons().size()) return;

        Pokemon target = playerTeam.getPokemons().get(index);

        // 2. Sécurité : On ne switch pas sur un Pokémon déjà actif ou mort
        if (target == playerActive) {
            battleLog.appendText("! " + target.getName() + " est déjà au combat.\n");
            return;
        }

        if (target.getHp().getStat() <= 0) {
            battleLog.appendText("! " + target.getName() + " est KO et ne peut pas combattre.\n");
            return;
        }

        // 3. Exécution du Switch
        battleLog.appendText("> " + playerActive.getName() + ", reviens ! En avant " + target.getName() + " !\n");

        playerTeam.setActivePokemonIndex(index);
        playerActive = target; // Mise à jour de la référence locale

        // 4. Rafraîchissement COMPLET de l'interface
        refreshUI();

        // 5. Conséquence : L'adversaire attaque (sauf si c'était un switch après un KO)
        setButtonsDisable(true);
        pause(1.0, () -> {
            executeOpponentAttack();
            setButtonsDisable(false); // On redonne la main après la riposte
        });
    }

    private void setupSwitchButtons() {
        List<Pokemon> members = playerTeam.getPokemons();

        // Bouton pour le 2ème Pokémon (index 1)
        if (members.size() > 1) {
            switchPoke1.setText(members.get(1).getName());
            switchPoke1.setVisible(true);
            switchPoke1.setDisable(members.get(1).getHp().getStat() <= 0); // Grisé si KO
        } else {
            switchPoke1.setVisible(false);
        }

        // Bouton pour le 3ème Pokémon (index 2)
        if (members.size() > 2) {
            switchPoke2.setText(members.get(2).getName());
            switchPoke2.setVisible(true);
            switchPoke2.setDisable(members.get(2).getHp().getStat() <= 0); // Grisé si KO
        } else {
            switchPoke2.setVisible(false);
        }
    }

    private void updateHpBar(ProgressBar bar, Pokemon p) {
        // On force le cast en double pour éviter que 80/100 ne donne 0 au lieu de 0.8
        double current = (double) p.getHp().getStat();
        double max = (double) p.getMaxHp();
        double ratio = current / max;

        // Mise à jour visuelle immédiate
        bar.setProgress(ratio);

        // Changement de couleur dynamique (Feedback visuel du PDF)
        if (ratio < 0.2) {
            bar.setStyle("-fx-accent: #e74c3c;"); // Rouge critique
        } else if (ratio < 0.5) {
            bar.setStyle("-fx-accent: #f1c40f;"); // Orange attention
        } else {
            bar.setStyle("-fx-accent: #2ecc71;"); // Vert santé
        }
    }

    private void setButtonsDisable(boolean state) {
        move1.setDisable(state); move2.setDisable(state);
        move3.setDisable(state); move4.setDisable(state);
        switchPoke1.setDisable(state); switchPoke2.setDisable(state);
    }

    private void checkBattleEnd() {
        if (playerTeam.isDefeated()) {
            battleLog.appendText("DEFAITE : Toute votre équipe est KO...\n");
            setButtonsDisable(true);
        } else if (opponentActive.getHp().getStat() <= 0) {
            battleLog.appendText("VICTOIRE : Baojian est vaincu !\n");
            setButtonsDisable(true);
        } else if (playerActive.getHp().getStat() <= 0) {
            // CAS CRITIQUE : Ton Pokémon est mort, on force le switch
            battleLog.appendText("Quel Pokémon doit remplacer " + playerActive.getName() + " ?\n");

            // On laisse les attaques grisées mais on réactive les switchs
            move1.setDisable(true); move2.setDisable(true);
            move3.setDisable(true); move4.setDisable(true);
            switchPoke1.setDisable(false);
            switchPoke2.setDisable(false);
            setupSwitchButtons(); // On rafraîchit les noms/états des boutons
        }
    }

    private void pause(double seconds, Runnable action) {
        PauseTransition pause = new PauseTransition(Duration.seconds(seconds));
        pause.setOnFinished(e -> action.run());
        pause.play();
    }
}