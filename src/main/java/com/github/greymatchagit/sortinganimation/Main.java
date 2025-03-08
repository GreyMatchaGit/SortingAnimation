package com.github.greymatchagit.sortinganimation;

import com.github.greymatchagit.sortinganimation.util.navigation.NavigationService;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        NavigationService.setStage(primaryStage);
        NavigationService.setMainScreenController(NavigationService.Page.MainScreen);
        NavigationService.navigateTo(NavigationService.Page.LoadingScreen);
    }
}
