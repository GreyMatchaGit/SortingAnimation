package com.github.greymatchagit.sortinganimation.models;

import com.github.greymatchagit.sortinganimation.util.constants.Constant;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Element<N> extends StackPane implements Cloneable {
    int id;
    Rectangle background;
    N value;
    boolean isSelected;

    public Element(Integer id, N value) {
        this.value = value;
        this.id = id;
        isSelected = false;
        background = generateBackground();
        setSelected(isSelected);
        Text text = new Text(value.toString());
        text.setFill(Color.web(Constant.Color.TEXT_BLUE));
        text.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        getChildren().addAll(background, text);
    }

    protected Rectangle generateBackground() {
        Rectangle rectangle = new Rectangle(50, 50);
        rectangle.setFill(Color.web(Constant.Color.BACKGROUND_BLUE));
        rectangle.setStroke(Color.web(Constant.Color.OUTLINE_BLUE));
        rectangle.setStrokeWidth(2);
        rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);
        return rectangle;
    }

    public boolean isSelected() { return isSelected; }

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

    public N getValue() {
        return value;
    }

    public Element<N> recreate() {
            Element<N> element = new Element<>(id, value);
            element.setSelected(isSelected);
            element.setLayoutX(getLayoutX());
            element.setLayoutY(getLayoutY());

            return element;
    }
}
