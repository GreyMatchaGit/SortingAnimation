package com.github.greymatchagit.sortinganimation.models;

import com.github.greymatchagit.sortinganimation.util.constants.Constant;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Element extends StackPane {
    Integer id;
    Rectangle background;
    Number value;

    public Element(Integer id, Number value) {
        this.value = value;
        this.id = id;

        background = new Rectangle(50, 50);
        background.setFill(Color.web(Constant.Color.BACKGROUND_BLUE));
        background.setStroke(Color.web(Constant.Color.OUTLINE_BLUE));
        background.setStrokeWidth(2);
        background.setArcWidth(20);
        background.setArcHeight(20);

        Text text = new Text(value.toString());
        text.setFill(Color.web(Constant.Color.TEXT_BLUE));
        text.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        getChildren().addAll(background, text);
    }

    public double width() {
        return background.getWidth();
    }

    public double height() {
        return background.getHeight();
    }

    public void setSelected(boolean isSelected) {
        if (isSelected) {
            background.setFill(Color.web(Constant.Color.BACKGROUND_GREEN));
            background.setStroke(Color.web(Constant.Color.OUTLINE_GREEN));
        } else {
            background.setFill(Color.web(Constant.Color.BACKGROUND_BLUE));
            background.setStroke(Color.web(Constant.Color.OUTLINE_BLUE));
        }
    }
}
