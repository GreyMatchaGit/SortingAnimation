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

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            testing(elements);
        }).start();
    }

    private void anotherTest() {
        ArrayList<Element<N>> elementsReplicas = new ArrayList<>();
        for (Element<N> e : elements) {
            Element<N> replica = e.recreate();
            elementsReplicas.add(replica);
        }

        Platform.runLater(() -> {
            for (Element<N> e : elementsReplicas) {
                displayPane.getChildren().add(e);
            }
        });

        ArrayList<TranslateTransition> transitions = new ArrayList<>();

        for (Element<N> e : elementsReplicas) {
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), e);
            translateTransition.setToY(200);
            transitions.add(translateTransition);
        }

        Platform.runLater(() -> transitions.forEach(TranslateTransition::play));
    }

    private void testing(ArrayList<Element<N>> originalPartition) {
        if (originalPartition.size() <= 1) return;
        ArrayList<Element<N>> partition = new ArrayList<>();
        for (Element<N> e : originalPartition) {
            Element<N> replica = e.recreate();
            partition.add(replica);
        }
        int middle = partition.size() / 2;
        if (partition.size() % 2 != 0) middle++;
        if (middle >= partition.size()) return;
        ArrayList<Element<N>> leftPartition = partitionAnimation(partition, 0, middle - 1, true);
        ArrayList<Element<N>> rightPartition = partitionAnimation(partition, middle, partition.size() - 1, false);
        testing(leftPartition);
        testing(rightPartition);
    }

    private ArrayList<Element<N>> partitionAnimation(ArrayList<Element<N>> parentPartition, int low, int high, boolean toLeft) {
        ArrayList<Element<N>> partition = new ArrayList<>();

        if (low > high)
            return partition;

        for (int i = low; i <= high; ++i) {
            Element<N> replica = parentPartition.get(i);
            partition.add(replica);
        }

        if (partition.isEmpty()) return partition;

        Platform.runLater(() -> {
            for (Element<N> e : partition) {
                displayPane.getChildren().add(e);
            }
        });

        int horizontalDirection = (int)((partition.getFirst().width() * partition.size()) / 2);
        if (toLeft) horizontalDirection *= -1;

        ArrayList<TranslateTransition> elementTranslates = new ArrayList<>();

        for (Element<N> element : partition) {
            TranslateTransition movePartitionDown = new TranslateTransition(Duration.millis(500), element);
            movePartitionDown.setToY(element.height() * 1.5);
            movePartitionDown.setToX(horizontalDirection);
            movePartitionDown.setOnFinished(_ -> {
                element.setLayoutX(element.getLayoutX() + element.getTranslateX());
                element.setLayoutY(element.getLayoutY() + element.getTranslateY());
                element.setTranslateX(0);
                element.setTranslateY(0);
            });
            elementTranslates.add(movePartitionDown);
        }

        Platform.runLater(() -> elementTranslates.forEach(TranslateTransition::play));

        try {
            Thread.sleep(550);
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
        leftToDown.setOnFinished(_ -> {
            left.setLayoutX(left.getLayoutX() + left.getTranslateX());
            left.setLayoutY(left.getLayoutY() + left.getTranslateY());
            left.setTranslateX(0);
            left.setTranslateY(0);
        });
        rightToUp.setOnFinished(_ -> {
            right.setLayoutX(right.getLayoutX() + right.getTranslateX());
            right.setLayoutY(right.getLayoutY() + right.getTranslateY());
            right.setTranslateX(0);
            right.setTranslateY(0);
        });

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
