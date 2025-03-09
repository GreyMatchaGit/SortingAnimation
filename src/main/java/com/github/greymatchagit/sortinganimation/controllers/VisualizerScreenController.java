package com.github.greymatchagit.sortinganimation.controllers;

import com.github.greymatchagit.sortinganimation.models.animation.QuickSortAnimation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VisualizerScreenController implements Initializable {

    @FXML
    ComboBox<String> cboxSortingSelector, cboxOne, cboxTwo, cboxThree, cboxFour;

    @FXML
    Button buttonStart, buttonSetElements, buttonRandomize;

    @FXML
    Pane displayPane;

    private boolean isRandomized = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> sortingAlgorithms = new ArrayList<>();
        sortingAlgorithms.add("Sorting Algo");
        sortingAlgorithms.add("Quick Sort");
        cboxSortingSelector.getItems().setAll(sortingAlgorithms);

        enableButton(buttonRandomize, isRandomized);
        enableButton(buttonSetElements, !isRandomized);
        enableButton(buttonStart, false);
        buttonStart.setDisable(true);

        buttonRandomize.setOnMouseReleased(e -> {
            actionRandomizer();
        });

        cboxSortingSelector.getSelectionModel()
            .selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> {
                if (!newValue.equals(oldValue)) {
                    displayPane.getChildren().clear();
                    actionSortingSelector();
                }
            }
        );

        buttonStart.setOnMouseReleased(e -> {
            actionStartButton();
        });
    }

    private void actionStartButton() {
        displayPane.getChildren().clear();
        String selectedSortingAlgorithm = cboxSortingSelector.getValue();

        switch (selectedSortingAlgorithm) {
            case "Quick Sort" -> startQuickSortAlgorithm();
        }
    }

    private void startQuickSortAlgorithm() {
        ArrayList<Integer> sample = new ArrayList<>();
        sample.add(34);
        sample.add(93);
        sample.add(54);
        sample.add(12);
        sample.add(79);
        sample.add(39);
        sample.add(7);
        sample.add(24);
        sample.add(81);
        sample.add(46);

        String partitioningType = "Lomuto";
        String pivotChoice = "First Index";
        QuickSortAnimation<Integer> algo = new QuickSortAnimation<>(displayPane, sample, partitioningType, pivotChoice);
        algo.start();

        sample.clear();
        sample.addAll(algo.getList());
    }

    private void setUpEmptySort() {
        cboxOne.setVisible(false);
        cboxTwo.setVisible(false);
        cboxThree.setVisible(false);
        cboxFour.setVisible(false);
        enableButton(buttonStart, false);
        buttonStart.setDisable(true);
    }

    private void setUpQuickSort() {
        cboxOne.setVisible(true);
        cboxTwo.setVisible(true);
        cboxThree.setVisible(false);
        cboxFour.setVisible(false);

        cboxOne.setPromptText("Select pivot");
        ArrayList<String> pivotChoices = new ArrayList<>();
        pivotChoices.add("First Element");
        pivotChoices.add("Median");
        pivotChoices.add("Random");
        cboxOne.getItems().setAll(pivotChoices);

        cboxTwo.setPromptText("Select partitioning");
        ArrayList<String> partitioningChoices = new ArrayList<>();
        partitioningChoices.add("Naive");
        partitioningChoices.add("Lomuto");
        partitioningChoices.add("Hoare's");
        cboxTwo.getItems().setAll(partitioningChoices);

        enableButton(buttonStart, true);
        buttonStart.setDisable(false);
    }

    private void actionSortingSelector() {
        String selectedAlgorithm = cboxSortingSelector.getValue();

        switch (selectedAlgorithm) {
            case "Sorting Algo" -> setUpEmptySort();
            case "Quick Sort" -> setUpQuickSort();
        }
    }

    private void actionRandomizer() {
        isRandomized = !isRandomized;
        enableButton(buttonRandomize, isRandomized);
        enableButton(buttonSetElements, !isRandomized);
        buttonSetElements.setDisable(isRandomized);
    }

    private void enableButton(Button button, boolean value) {
        if (value) {
            button.setStyle(
                    "-fx-background-color: #dcfce7; " +
                    "-fx-border-color: #22c55e; " +
                    "-fx-border-width: 2px; " +
                    "-fx-border-radius: 20px; " +
                    "-fx-background-radius: 20px; " +
                    "-fx-text-fill: #166534; " +
                    "-fx-font-size: 14px; " +
                    "-fx-padding: 8px 16px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-cursor: hand;"
            );

            button.setOnMouseEntered(e -> button.setStyle(
                    "-fx-background-color: #bbf7d0; " +
                    "-fx-border-color: #16a34a; " +
                    "-fx-border-width: 2px; " +
                    "-fx-border-radius: 20px; " +
                    "-fx-background-radius: 20px; " +
                    "-fx-text-fill: #166534; " +
                    "-fx-font-size: 14px; " +
                    "-fx-padding: 8px 16px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-cursor: hand;"
            ));

            button.setOnMouseExited(e -> button.setStyle(
                    "-fx-background-color: #dcfce7; " +
                    "-fx-border-color: #22c55e; " +
                    "-fx-border-width: 2px; " +
                    "-fx-border-radius: 20px; " +
                    "-fx-background-radius: 20px; " +
                    "-fx-text-fill: #166534; " +
                    "-fx-font-size: 14px; " +
                    "-fx-padding: 8px 16px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-cursor: hand;"
            ));
        } else {
            button.setStyle(
                    "-fx-background-color: #f3f4f6; " +
                    "-fx-border-color: #6b7280; " +
                    "-fx-border-width: 2px; " +
                    "-fx-border-radius: 20px; " +
                    "-fx-background-radius: 20px; " +
                    "-fx-text-fill: #374151; " +
                    "-fx-font-size: 14px; " +
                    "-fx-padding: 8px 16px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-cursor: hand;"
            );

            button.setOnMouseEntered(e -> button.setStyle(
                    "-fx-background-color: #e5e7eb; " +
                    "-fx-border-color: #4b5563; " +
                    "-fx-border-width: 2px; " +
                    "-fx-border-radius: 20px; " +
                    "-fx-background-radius: 20px; " +
                    "-fx-text-fill: #374151; " +
                    "-fx-font-size: 14px; " +
                    "-fx-padding: 8px 16px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-cursor: hand;"
            ));

            button.setOnMouseExited(e -> button.setStyle(
                    "-fx-background-color: #f3f4f6; " +
                    "-fx-border-color: #6b7280; " +
                    "-fx-border-width: 2px; " +
                    "-fx-border-radius: 20px; " +
                    "-fx-background-radius: 20px; " +
                    "-fx-text-fill: #374151; " +
                    "-fx-font-size: 14px; " +
                    "-fx-padding: 8px 16px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-cursor: hand;"
            ));
        }
    }
}
