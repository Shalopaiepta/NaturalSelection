package com.selection.naturalselection;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Food extends Circle {

    public Food(double x, double y) {
        super(x, y, 3); // Радиус 3 для визуализации пищи
        this.setFill(Color.GREEN); // Цвет пищи
    }
}
