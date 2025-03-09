package com.github.greymatchagit.sortinganimation.util.navigation;

import com.github.greymatchagit.sortinganimation.controllers.MainScreenController;
import com.github.greymatchagit.sortinganimation.util.constants.Constant;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NavigationService {

    /**
     *  A class to store all the existing fxml files.
     */
    public static class Page {
        public static final String MainScreen = "MainScreen.fxml";
        public static final String LoadingScreen = "LoadingScreen.fxml";
        public static final String VisualizerScreen = "VisualizerScreen.fxml";
    }

    private static Stage stage = null;
    private static MainScreenController mainScreenController = null;
    private static final Logger logger = Logger.getLogger("NavigationService");
    private static Boolean withTransition = false;

    public static void setStage(Stage stage) {
        if (NavigationService.stage != null)
            return;

        if (stage == null) {
            logger.log(Level.SEVERE, "The stage passed is null.");
            return;
        }

        NavigationService.stage = stage;
        logger.log(Level.INFO, "Stage has been set.");
    }

    public static MainScreenController getMainScreenController() {
        return mainScreenController;
    }

    public static void setMainScreenController(String fxmlName) {
        final String FXML_PATH =
            Constant.Navigation.GROUP_NAME_PATH +
            Constant.Navigation.FXML_DIR +
            fxmlName;

        final URL FXML_URL = Constant.class.getResource(FXML_PATH);

        try {
            if (FXML_URL == null)
                throw new IllegalArgumentException();

            FXMLLoader loader = new FXMLLoader(FXML_URL);
            Parent parent = loader.load();
            mainScreenController = loader.getController();

            if (mainScreenController == null) {
                logger.log(
                    Level.SEVERE,
                    "mainScreen is still null after loader.getController"
                );
            }

            if (parent == null) {
                logger.log(
                        Level.SEVERE,
                        "parent is still null"
                );
            }

            Scene scene = new Scene(parent);

            stage.setFullScreen(true);
            stage.setScene(scene);
            stage.show();
        } catch (IllegalArgumentException e) {
            logger.log(
                Level.WARNING,
                "FXML URL is null"
            );
        } catch (NullPointerException e) {
            logger.log(
                Level.WARNING,
                "NavigationService.stage has not yet been set."
            );
        } catch (IOException e) {
            logger.log(
                Level.WARNING,
                String.format("FXML File: \"%s\" cannot be found", fxmlName)
            );
        }
    }

    public static void setEnableTransition(Boolean value) {
        withTransition = value;
    }

    public static void navigateTo(String fxmlName) {
        final String FXML_PATH =
            Constant.Navigation.GROUP_NAME_PATH +
            Constant.Navigation.FXML_DIR +
            fxmlName;

        final URL FXML_URL = Constant.class.getResource(FXML_PATH);

        try {
            if (FXML_URL == null)
                throw new IllegalArgumentException();

            FXMLLoader loader = new FXMLLoader(FXML_URL);
            Parent content = loader.load();

            AnchorPane.setBottomAnchor(content, 0.0);
            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);

            if (withTransition) {

                Platform.runLater(() -> {
                    FadeTransition fadeOut = new FadeTransition(Duration.millis(500), mainScreenController.getMainScreen());
                    fadeOut.setFromValue(1.0);
                    fadeOut.setToValue(0.0);
                    fadeOut.setOnFinished(event -> {
                        mainScreenController.clearContent();
                        mainScreenController.setContent(content);
                        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), mainScreenController.getMainScreen());
                        fadeIn.setFromValue(0.0);
                        fadeIn.setToValue(1.0);
                        fadeIn.play();
                    });
                    fadeOut.play();
                });

            } else {
                mainScreenController.clearContent();
                mainScreenController.setContent(content);
            }

        } catch (IllegalArgumentException e) {
            logger.log(
                Level.WARNING,
                "FXML URL is null"
            );
        } catch (NullPointerException e) {
            logger.log(
                Level.WARNING,
                "NavigationService.stage has not yet been set."
            );
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.log(
                Level.SEVERE,
                String.format("FXML File: \"%s\" cannot be found", fxmlName)
            );
            throw new RuntimeException(e);
        }
    }
}
