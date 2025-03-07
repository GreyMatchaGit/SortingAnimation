package com.github.greymatchagit.sortinganimation.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Time;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadingScreenController implements Initializable {

    @FXML
    private VBox vboxParent;

    @FXML
    private AnchorPane anchorpaneParent;

    @FXML
    private Text txtActionMessage;

    @FXML
    private Circle circleLarge, circleMedium, circleSmall;

    @FXML
    private StackPane stackpaneActionMessage;

    private final Logger logger = Logger.getLogger(LoadingScreenController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        vboxParent.setOnMousePressed(_ -> System.out.println("Mouse is clicked!"));

        Platform.runLater(() -> {
            animateActionMessage();
            animateCircle(circleLarge, 6);
            animateCircle(circleMedium, 8);
            animateCircle(circleSmall, 10);
        });
    }

    private void animateActionMessage() {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), stackpaneActionMessage);
        scaleTransition.setByX(-0.05);
        scaleTransition.setByY(-0.05);
        scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
        logger.log(Level.INFO, "Action message animation started.");
    }

    private void animateCircle(Circle circle, Integer speed) {
        Random random = new Random();

        int topBorder = (int)circle.getRadius();
        int leftBorder = (int)circle.getRadius();
        int rightBorder = (int)(anchorpaneParent.getWidth() - circle.getRadius());
        int bottomBorder = (int)(anchorpaneParent.getHeight() - circle.getRadius());

        circle.setLayoutX(random.nextInt(leftBorder, rightBorder));
        circle.setLayoutY(random.nextInt(topBorder, bottomBorder));

        final int[] xDirection = {speed};
        final int[] yDirection = {speed};

        Timeline circleMovement = new Timeline(new KeyFrame(Duration.millis(24), e-> {
            int currentX = (int) circle.getLayoutX();
            int currentY = (int) circle.getLayoutY();

            if (currentX <= leftBorder || currentX >= rightBorder) {
                xDirection[0] = -xDirection[0];
            }

            if (currentY <= topBorder || currentY >= bottomBorder) {
                yDirection[0] = -yDirection[0];
            }

            circle.setLayoutX(currentX + xDirection[0]);
            circle.setLayoutY(currentY + yDirection[0]);
        }));

        circleMovement.setCycleCount(Timeline.INDEFINITE);
        circleMovement.play();
    }
}
