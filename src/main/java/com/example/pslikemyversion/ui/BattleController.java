package com.example.pslikemyversion.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;

public class BattleController {
    @FXML private TextArea battleLog;
    @FXML private ProgressBar playerHpBar;
    @FXML private ProgressBar opponentHpBar;
    @FXML private Label opponentName;
    @FXML private Label playerName;
    @FXML private Button move1, move2, move3, move4;
}
