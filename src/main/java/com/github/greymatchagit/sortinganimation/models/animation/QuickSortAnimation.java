package com.github.greymatchagit.sortinganimation.models.animation;

import com.github.greymatchagit.sortinganimation.models.Element;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class QuickSortAnimation<N> {
    ArrayList<N> list;
    ArrayList<Element<N>> elements;
    String partitioningType;
    String pivotChoice;
    Pane displayPane;

    /**
     * TODO: Check the Thread sleeps make sure that it doesn't interrupt the animations by
     *  putting them inside Platform.runLater();
     */
    public QuickSortAnimation(Pane displayPane, ArrayList<? extends Number> list, String partitioningType, String pivotChoice) {
        this.displayPane = displayPane;
        this.list = (ArrayList<N>) list;
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
        for (N number : list) {
            Element<N> element = new Element<N>(counter++, number);
            elements.add(element);
        }
    }

    private void setUpElementsInPane() {
        ArrayList<FadeTransition> elementTransitions = new ArrayList<>();
        final int gapBetweenElements = 5;
        for (int i = 0; i < elements.size(); ++i) {
            Element<N> current = elements.get(i);
            current.setLayoutX((i * (current.width() + gapBetweenElements) + displayPane.getWidth() / 2) - elements.size() * (elements.getFirst().width()) / 2);
            current.setLayoutY(200);
            elementTransitions.add(fadeInElement(current));
            displayPane.getChildren().add(current);
        }
        Platform.runLater(() -> elementTransitions.forEach(FadeTransition::play));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        testing();
    }

    private FadeTransition fadeInElement(Element<N> element) {
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
            for (int i = 0; i < elements.size() / 2; ++i) {
                int left = i;
                int right = elements.size() - i - 1;
                elements.get(left).setSelected(true);
                elements.get(right).setSelected(true);
                Platform.runLater(() -> swapAnimation(left, right));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                elements.get(left).setSelected(false);
                elements.get(right).setSelected(false);
            }

//            Platform.runLater(() -> testing(elements));
        }).start();
    }

    private void testing(ArrayList<Element<N>> partition) {
        if (partition.size() <= 1) return;
        int middle = partition.size() / 2;
        if (middle == 0 || middle >= partition.size()) return;
        ArrayList<Element<N>> leftPartition = partitionAnimation(partition, 0, middle, true);
        ArrayList<Element<N>> rightPartition = partitionAnimation(partition, middle + 1, partition.size() - 1, false);
        testing(leftPartition);
        testing(rightPartition);
    }

    private ArrayList<Element<N>> partitionAnimation(ArrayList<Element<N>> parentPartition, int low, int high, boolean toLeft) {
        ArrayList<Element<N>> partition = new ArrayList<>();
        if (low >= high)
            return partition;

        new Thread(() -> {
            for (int i = low; i <= high; ++i) {
                Element<N> original = parentPartition.get(i);
                N value = original.getValue();
                Element<N> element = new Element<>(0, value);
                element.setLayoutY(original.getLayoutY());
                element.setLayoutX(original.getLayoutX());
                element.setSelected(element.isSelected());
                if (element.hashCode() == original.hashCode())
                    System.out.println("They're the same shit.");
                partition.add(element);
            }

            Platform.runLater(() -> {
                partition.forEach(e -> displayPane.getChildren().add(e));
            });
        }).start();

        if (partition.isEmpty()) return partition;

        int horizontalDirection = (int)((partition.getFirst().height() * partition.size()) / 2);
        if (toLeft) horizontalDirection *= -1;

        ArrayList<TranslateTransition> elementTranslates = new ArrayList<>();

        for (Element<N> element : partition) {
            TranslateTransition movePartitionDown = new TranslateTransition(Duration.millis(500), element);
            movePartitionDown.setToY(element.height() * 2);
            movePartitionDown.setToX(horizontalDirection);
            elementTranslates.add(movePartitionDown);
        }

        Platform.runLater(() -> elementTranslates.forEach(TranslateTransition::play));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return partition;
    }

    private void swapAnimation(int leftIndex, int rightIndex) {
        Element<N> left = elements.get(leftIndex);
        Element<N> right = elements.get(rightIndex);

        // First vertical translate
        TranslateTransition leftToUp = new TranslateTransition(Duration.millis(200), left);
        leftToUp.setToY(-left.height() - 10);

        TranslateTransition rightToDown = new TranslateTransition(Duration.millis(200), right);
        rightToDown.setToY(right.height() + 10);

        // Horizontal translate
        TranslateTransition leftToRight = new TranslateTransition(Duration.millis(600), left);
        leftToRight.setToX(right.getLayoutX() - left.getLayoutX());
        TranslateTransition rightToLeft = new TranslateTransition(Duration.millis(600), right);
        rightToLeft.setToX(left.getLayoutX() - right.getLayoutX());

        // Final vertical translate, remember left and right are swapped at this point but their names retain.
        TranslateTransition leftToDown = new TranslateTransition(Duration.millis(200), left);
        leftToDown.setToY(0);
        TranslateTransition rightToUp = new TranslateTransition(Duration.millis(200), right);
        rightToUp.setToY(0);

        // On finish
        leftToUp.setOnFinished(_ -> leftToRight.play());
        rightToDown.setOnFinished(_ -> rightToLeft.play());
        leftToRight.setOnFinished(_ -> leftToDown.play());
        rightToLeft.setOnFinished(_ -> rightToUp.play());

        // Play altogether
        leftToUp.play();
        rightToDown.play();

        // Change actual values in the array
        elements.set(leftIndex, right);
        elements.set(rightIndex, left);
    }

    public ArrayList<N> getList() {
        list.clear();
        for (Element<N> element : elements) {
            list.add(element.getValue());
        }
        return list;
    }
}
