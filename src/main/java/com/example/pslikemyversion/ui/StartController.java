package com.example.pslikemyversion.ui;

import com.example.pslikemyversion.core.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

public class StartController {
    @FXML
    private void onStartClicked(ActionEvent event) {
        SceneNavigator.switchScene((Node) event.getSource(), "teambuild-view.fxml");
    }
}
