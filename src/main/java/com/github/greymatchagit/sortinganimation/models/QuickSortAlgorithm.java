package com.github.greymatchagit.sortinganimation.models;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;

public class QuickSortAlgorithm {
    ArrayList<? extends Number> list;
    ArrayList<Element> elements;
    String partitioningType;
    String pivotChoice;
    Pane displayPane;

    public QuickSortAlgorithm(Pane displayPane, ArrayList<? extends Number> list, String partitioningType, String pivotChoice) {
        this.displayPane = displayPane;
        this.list = list;
        this.partitioningType = partitioningType;
        this.pivotChoice = pivotChoice;
        elements = new ArrayList<>();
        displayPane.setPadding(new Insets(50));
    }

    public void start() {
        Integer pivot = getPivot();
        Platform.runLater(() -> {
            setUpElementsInPane();
        });
    }

    private void setUpElementsInPane() {
        int counter = 0;
        for (Number number : list) {
            Element element = new Element(counter++, number);
            elements.add(element);
        }

        for (int i = 0; i < elements.size(); ++i) {
            Element current = elements.get(i);
            current.setLayoutX(i * 50 + 5);
            current.setLayoutY(0);

            displayPane.getChildren().add(current);
        }
    }

    private Integer getPivot() {
        return switch (pivotChoice) {
            case "First Index" -> 0;
            case "Median" -> list.size() / 2;
            case "Random" -> {
                Random random = new Random();
                yield random.nextInt(list.size());
            }
            default -> -1;
        };
    }
}
