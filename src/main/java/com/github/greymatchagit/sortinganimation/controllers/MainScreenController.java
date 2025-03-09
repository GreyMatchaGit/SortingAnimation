package com.github.greymatchagit.sortinganimation.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    @FXML
    private AnchorPane mainScreen;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public AnchorPane getMainScreen() {
        return mainScreen;
    }

    public void clearContent() {
        mainScreen.getChildren().clear();
    }

    public void setContent(Node node) {
        mainScreen.getChildren().setAll(node);
    }
}
