package com.github.greymatchagit.sortinganimation.util.navigation;

import com.github.greymatchagit.sortinganimation.util.constants.Constant;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NavigationService {

    /**
     *  A class to store all the existing fxml files.
     */
    public static class Page {
        public static final String LoadingScreen = "LoadingScreen.fxml";
    }

    private static Stage stage = null;
    private static final Logger logger = Logger.getLogger("NavigationService");

    public static void setStage(Stage stage) {
        if (NavigationService.stage != null)
            return;

        if (stage == null) {
            logger.log(Level.WARNING, "The stage passed is null.");
            return;
        }

        NavigationService.stage = stage;
        logger.log(Level.INFO, "Stage has been set.");
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
            Scene scene = new Scene(FXMLLoader.load(FXML_URL));
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
            throw new RuntimeException(e);
        }
    }
}
