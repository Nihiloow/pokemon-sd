package com.example.pslikemyversion.ui;

import com.example.pslikemyversion.core.GameSession;
import com.example.pslikemyversion.engine.CombatSystem;
import com.example.pslikemyversion.logic.moves.Move;
import com.example.pslikemyversion.logic.passive.PassiveFactory;
import com.example.pslikemyversion.logic.pokemons.Pokedex;
import com.example.pslikemyversion.logic.pokemons.Pokemon;
import com.example.pslikemyversion.logic.pokemons.Team;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import java.util.List;
import javafx.scene.layout.HBox;

public class BattleController {
    @FXML private TextArea battleLog;
    @FXML private ProgressBar playerHpBar, opponentHpBar;
    @FXML private Label playerName, opponentName;
    @FXML private Button move1, move2, move3, move4;
    @FXML private HBox switchActionsBox;

    private Pokemon playerActive;
    private Pokemon opponentActive;
    private Team playerTeam;
    private Team opponentTeam;
    private boolean isForcedSwitch = false;
    private int turnCount = 1;

    @FXML
    public void initialize() {
        // loads team
        playerTeam = GameSession.getInstance().getPlayerTeam();
        playerActive = playerTeam.getActivePokemon();

        // creates enemy team
        opponentTeam = new Team();
        String[] enemies = {"Cerfrousse", "Limonde", "Kraknoix"};
        for (String name : enemies) {
            Pokemon p = Pokedex.getPokemonByName(name);
            if (p != null) {
                p.getTypes().addAll(Pokedex.getTypesFor(name));
                List<String> abilities = Pokedex.getAbilitiesForPokemon(name);
                if (!abilities.isEmpty()) p.setAbility(PassiveFactory.createAbility(abilities.get(0)));

                List<String> moves = Pokedex.getMovesForPokemon(name);
                for (int i = 0; i < Math.min(2, moves.size()); i++) {
                    p.getMoveSet().add(Pokedex.getMoveByName(moves.get(i)));
                }
                opponentTeam.addPokemon(p);
            }
        }
        opponentActive = opponentTeam.getActivePokemon();

        refreshUI();
        setupSwitchButtons();
    }

    private void refreshUI() {
        if (playerActive == null || opponentActive == null) return;

        playerName.setText(playerActive.getName());
        opponentName.setText(opponentActive.getName());
        updateHpBar(playerHpBar, playerActive);
        updateHpBar(opponentHpBar, opponentActive);

        // links buttons to moves
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
        battleLog.appendText("\n--- TURN " + turnCount + " ---\n");
        turnCount++;

        // Speed Check
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
            checkBattleEnd();
        } else {
            // gives back control if no one ko
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
        Pokemon target = playerTeam.getPokemons().get(index);

        // switch logic
        playerTeam.setActivePokemonIndex(index);
        playerActive = target;
        refreshUI();
        setupSwitchButtons();

        if (isForcedSwitch) { // forced ko switch
            battleLog.appendText("> " + playerActive.getName() + " entre sur le terrain !\n");
            isForcedSwitch = false; // Reset du flag
            setButtonsDisable(false);
        } else { // manual switch
            battleLog.appendText("> Reviens ! En avant " + playerActive.getName() + " !\n");
            setButtonsDisable(true);
            pause(1.0, () -> {
                executeOpponentAttack();
            });
        }
    }

    private void setupSwitchButtons() {
        switchActionsBox.getChildren().clear();
        List<Pokemon> members = playerTeam.getPokemons();

        for (int i = 0; i < members.size(); i++) { // switch buttons
            Pokemon p = members.get(i);
            Button btn = new Button(p.getName());

            btn.setPrefWidth(100); // Design du bouton
            btn.setStyle("-fx-background-radius: 10;");

            boolean isCurrent = (p == playerActive); // can't switch on ko pokemon
            boolean isDead = (p.getHp().getStat() <= 0);

            if (isCurrent) btn.setStyle("-fx-background-color: #add8e6; -fx-background-radius: 10;");
            btn.setDisable(isCurrent || isDead);
            final int index = i;
            btn.setOnAction(e -> onSwitchRequested(index));
            switchActionsBox.getChildren().add(btn);
        }
    }

    private void updateHpBar(ProgressBar bar, Pokemon p) {
        double current = (double) p.getHp().getStat();
        double max = (double) p.getMaxHp();
        double ratio = current / max;

        // update
        bar.setProgress(ratio);

        // dynamic colours
        if (ratio < 0.2) {
            bar.setStyle("-fx-accent: #e74c3c;");
        } else if (ratio < 0.5) {
            bar.setStyle("-fx-accent: #f1c40f;");
        } else {
            bar.setStyle("-fx-accent: #2ecc71;");
        }
    }

    private void setButtonsDisable(boolean state) {
        move1.setDisable(state);
        move2.setDisable(state);
        move3.setDisable(state);
        move4.setDisable(state);

        if (state) {
            // blocks everything
            switchActionsBox.getChildren().forEach(node -> node.setDisable(true));
        } else {
            // partial block
            setupSwitchButtons();
        }
    }

    private void checkBattleEnd() {
        if (playerTeam.isDefeated()) {
            battleLog.appendText("\nDEFEAT: All your team is KO...");
            setButtonsDisable(true);
        } else if (opponentTeam.isDefeated()) {
            battleLog.appendText("\nVICTORY: The opponent team is defeated!");
            setButtonsDisable(true);
        } else if (playerActive.getHp().getStat() <= 0) {
            isForcedSwitch = true;
            battleLog.appendText("\nWho will replace " + playerActive.getName() + "?");
            setButtonsDisable(true);
            setupSwitchButtons();
        } else if (opponentActive.getHp().getStat() <= 0) { // auto switch enemy
            for (int i = 0; i < opponentTeam.getPokemons().size(); i++) {
                Pokemon next = opponentTeam.getPokemons().get(i);
                if (next.getHp().getStat() > 0) {
                    opponentTeam.setActivePokemonIndex(i);
                    opponentActive = next;
                    battleLog.appendText("\nOpponent sends " + opponentActive.getName() + "!");
                    refreshUI();
                    setButtonsDisable(false); // player can attack new enemy
                    return;
                }
            }
        }
    }

    private void pause(double seconds, Runnable action) {
        PauseTransition pause = new PauseTransition(Duration.seconds(seconds));
        pause.setOnFinished(e -> action.run());
        pause.play();
    }

    private void applyEndTurnEffects() {
        // player effect
        if (playerActive.getStatus() != null) {
            playerActive.getStatus().applyEffect(playerActive, opponentActive);
            battleLog.appendText("\n" + playerActive.getName() + " is hurt by its " + playerActive.getStatus().getName() + "!");
        }

        // enemy effect
        if (opponentActive.getStatus() != null) {
            opponentActive.getStatus().applyEffect(opponentActive, playerActive);
            battleLog.appendText("\n" + opponentActive.getName() + " is hurt by its " + opponentActive.getStatus().getName() + "!");
        }

        // vasual update
        updateHpBar(playerHpBar, playerActive);
        updateHpBar(opponentHpBar, opponentActive);

        checkBattleEnd(); // checks for ko
    }
}