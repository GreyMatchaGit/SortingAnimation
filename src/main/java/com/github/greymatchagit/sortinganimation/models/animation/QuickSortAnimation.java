package com.github.greymatchagit.sortinganimation.models.animation;

import com.github.greymatchagit.sortinganimation.models.Element;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class QuickSortAnimation {
    ArrayList<? extends Number> list;
    ArrayList<Element> elements;
    String partitioningType;
    String pivotChoice;
    Pane displayPane;

    public QuickSortAnimation(Pane displayPane, ArrayList<? extends Number> list, String partitioningType, String pivotChoice) {
        this.displayPane = displayPane;
        this.list = list;
        this.partitioningType = partitioningType;
        this.pivotChoice = pivotChoice;
        elements = new ArrayList<>();
    }

    public void start() {
        Integer pivot = getPivot();
        createElements();

        Platform.runLater(() -> {
            setUpElementsInPane();
        });
    }

    private void createElements() {
        int counter = 0;
        for (Number number : list) {
            Element element = new Element(counter++, number);
            elements.add(element);
        }
    }

    private void setUpElementsInPane() {
        ArrayList<FadeTransition> elementTransitions = new ArrayList<>();
        final int gapBetweenElements = 5;
        for (int i = 0; i < elements.size(); ++i) {
            Element current = elements.get(i);
            current.setLayoutX((i * (current.width() + gapBetweenElements) + displayPane.getWidth() / 2) - elements.size() * (elements.getFirst().width()) / 2);
            current.setLayoutY(50);
            elementTransitions.add(fadeInElement(current));
            displayPane.getChildren().add(current);
        }
        elementTransitions.forEach(FadeTransition::play);
        testing();
    }

    private FadeTransition fadeInElement(Element element) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), element);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        return fadeTransition;
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

    private void testing() {
        new Thread(() -> {
            for (Element element : elements) {
                element.setSelected(true);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                element.setSelected(false);
            }
        }).start();
    }
}
