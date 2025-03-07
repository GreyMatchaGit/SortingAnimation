module com.github.greymatchagit.sortinganimation {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.github.greymatchagit.sortinganimation to javafx.fxml;
    exports com.github.greymatchagit.sortinganimation;
}