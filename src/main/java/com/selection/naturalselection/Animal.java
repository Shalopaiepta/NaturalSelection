package com.selection.naturalselection;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Animal extends Circle {
    private double energy;

    public Animal(double x, double y, double energy) {
        super(x, y, 5); // Радиус 5 для визуализации животного
        this.energy = energy;
        this.setFill(Color.BLUE); // Цвет животного
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public void moveTowards(Food food) {
        // Реализация движения к пище
        double dx = food.getCenterX() - this.getCenterX();
        double dy = food.getCenterY() - this.getCenterY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 1) {
            this.setCenterX(this.getCenterX() + dx / distance);
            this.setCenterY(this.getCenterY() + dy / distance);
        }
    }

    public boolean isFoodFound(Food food) {
        // Проверка, достигло ли животное пищи
        return this.getBoundsInParent().intersects(food.getBoundsInParent());
    }
}
