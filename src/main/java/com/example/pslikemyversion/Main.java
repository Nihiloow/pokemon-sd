package com.example.pslikemyversion;

import com.example.pslikemyversion.core.AppLauncher;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        AppLauncher.init(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
