package com.github.greymatchagit.sortinganimation.models.animation;

import com.github.greymatchagit.sortinganimation.models.Element;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public abstract class SortAnimation<N> {
    Pane displayPane;
    ArrayList<N> list;
    ArrayList<Element<N>> elements;

    public SortAnimation(Pane displayPane, ArrayList<? extends Number> list) {
        this.displayPane = displayPane;
        this.list = (ArrayList<N>) list;
    }

    public abstract void start();
}
