package com.github.greymatchagit.sortinganimation.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    @FXML
    public AnchorPane mainScreen;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("MainScreenController: Finished initializing.");
    }

    public AnchorPane getMainScreen() {
        return mainScreen;
    }

    public void setContent(Node node) {
        while (mainScreen == null) {

        }
        mainScreen.getChildren().setAll(node);
    }
}
