package com.selection.naturalselection;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Food extends Circle {

    public Food(double x, double y) {
        super(x, y, 5);
        this.setFill(Color.GREEN);  // Устанавливаем начальный цвет пищи
    }

    public void setColor(Color color) {
        this.setFill(color);
    }

}
