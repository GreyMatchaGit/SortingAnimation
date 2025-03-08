package com.github.greymatchagit.sortinganimation.controllers;

import com.github.greymatchagit.sortinganimation.util.navigation.NavigationService;
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

    private AnchorPane mainScreen;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(() -> {
            while (NavigationService.getMainScreenController().getMainScreen() == null) {
                System.out.println("Main Screen from Controller is still null");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            Platform.runLater(() -> {
                mainScreen = NavigationService.getMainScreenController().getMainScreen();
                if (mainScreen == null) {
                    logger.log(Level.SEVERE, "mainScreen is null.");
                }
                animateActionMessage();
                animateCircle(circleLarge, 6);
                animateCircle(circleMedium, 8);
                animateCircle(circleSmall, 10);
            });
        }).start();

        vboxParent.setOnMouseClicked(_ -> {
            NavigationService.navigateTo(
                NavigationService.Page.VisualizerScreen
            );
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
        int rightBorder = (int)(mainScreen.getWidth() - circle.getRadius());
        int bottomBorder = (int)(mainScreen.getMinHeight() - circle.getRadius());

        circle.setLayoutX(random.nextInt(leftBorder, rightBorder));
        circle.setLayoutY(random.nextInt(topBorder, bottomBorder));

        final int[] xDirection = {speed};
        final int[] yDirection = {speed};

        Timeline circleMovement = new Timeline(new KeyFrame(Duration.millis(24), e-> {
            int currentX = (int) circle.getLayoutX();
            int currentY = (int) circle.getLayoutY();

            int dynamicTopBorder = (int)circle.getRadius();
            int dynamicLeftBorder = (int)circle.getRadius();
            int dynamicRightBorder = (int)(mainScreen.getWidth() - circle.getRadius());
            int dynamicBottomBorder = (int)(mainScreen.getHeight() - circle.getRadius());

            if (currentX <= dynamicLeftBorder || currentX >= dynamicRightBorder) {
                xDirection[0] = -xDirection[0];
            }

            if (currentY <= dynamicTopBorder || currentY >= dynamicBottomBorder) {
                yDirection[0] = -yDirection[0];
            }

            circle.setLayoutX(currentX + xDirection[0]);
            circle.setLayoutY(currentY + yDirection[0]);
        }));

        circleMovement.setCycleCount(Timeline.INDEFINITE);
        circleMovement.play();
    }
}
