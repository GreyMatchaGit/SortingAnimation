package com.github.greymatchagit.sortinganimation.util.navigation;

import com.github.greymatchagit.sortinganimation.util.constants.Constant;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import java.io.IOException;

public class NavigationService {

    public static class Page {
        public static final String MainMenu = "MainMenu";
    }

    private static Stage stage = null;

    public static boolean setStage(Stage stage) {
        if (stage != null)
            return false;
        NavigationService.stage = stage;
        return true;
    }

    public static void navigateTo(String fxmlName) {
        try {
            stage.setScene(
                FXMLLoader.load(
                    Constant.class.getResource(
                        Constant.Navigation.GROUP_NAME_PATH + fxmlName + ".fxml"
                    )
                )
            );

            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(
                String.format(
                    "FXML File: \"%s.fxml\" cannot be found", fxmlName
                )
            );
        }
    }
}
