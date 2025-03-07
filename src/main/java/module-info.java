module com.github.greymatchagit.sortinganimation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.github.greymatchagit.sortinganimation to javafx.fxml;
    exports com.github.greymatchagit.sortinganimation;
    exports com.github.greymatchagit.sortinganimation.controllers;
}