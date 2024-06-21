package com.selection.naturalselection;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Animal extends ImageView {
    private double energy;
    private double speed;
    private double maxspeed;
    private double minspeed;
    private static final double ANIMAL_SIZE = 20;
    private static final Random random = new Random();
    private int foodCount = 0;

    public Animal(double x, double y, double energy) {
        super(new Image(Animal.class.getResourceAsStream("/com/selection/naturalselection/spore.png"))); // Путь к твоему изображению
        this.setX(x);
        this.speed = random.nextDouble();
        this.maxspeed=speed*3;
        this.minspeed=speed/3;
        this.setY(y);
        this.energy = energy;
        this.setFitWidth(ANIMAL_SIZE); // Установка ширины изображения
        this.setFitHeight(ANIMAL_SIZE); // Установка высоты изображения
        this.setPreserveRatio(true); // Сохранение пропорций изображения

        // Применение случайного цвета
        applyRandomColor();
    }

    private void applyRandomColor() {
        // Генерация случайного оттенка
        double hue = random.nextDouble() * 360 - 180;

        // Создание ColorAdjust для изменения оттенка
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(hue / 360.0);

        // Применение эффекта к изображению
        this.setEffect(colorAdjust);
    }

    public double getEnergy() {
        return energy;
    }
    public double getspeed() {
        return speed;
    }
    public double getmaxspeed() {
        return maxspeed;
    }
    public double getminspeed() {
        return minspeed;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }
    public void setspeed(double speed) {
        this.speed = speed;
    }
    public void setmaxspeed(double maxspeed) {
        this.maxspeed = maxspeed;
    }
    public void setminspeed(double minspeed) {
        this.minspeed = minspeed;
    }

    public void moveTowards(Food food) {
        // Реализация движения к пище
        double dx = food.getCenterX() - this.getX();
        double dy = food.getCenterY() - this.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 1) {
            this.setX(this.getX() + dx / distance);
            this.setY(this.getY() + dy / distance);
        }
    }

    public boolean isFoodFound(Food food) {
        // Проверка, достигло ли животное пищи
        return this.getBoundsInParent().intersects(food.getBoundsInParent());
    }

    public boolean isColliding(Animal other) {
        return this.getBoundsInParent().intersects(other.getBoundsInParent());
    }

    public void resolveCollision(Animal other) {
        double dx = this.getX() - other.getX();
        double dy = this.getY() - other.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < ANIMAL_SIZE) {
            double overlap = ANIMAL_SIZE - distance;
            this.setX(this.getX() + dx / distance * overlap / 2);
            this.setY(this.getY() + dy / distance * overlap / 2);
            other.setX(other.getX() - dx / distance * overlap / 2);
            other.setY(other.getY() - dy / distance * overlap / 2);
        }
    }

    public void incrementFoodCount(Simulation simulation) {
        foodCount++;

        if (foodCount >= 3) {
            // Создание нового животного такого же цвета
            Animal newAnimal = new Animal(this.getX(), this.getY(), 10);
            newAnimal.setEffect(this.getEffect());

            // Шанс изменения скорости нового животного
            if (random.nextDouble() < 0.9) { // Например, 50% шанс

                newAnimal.setspeed(Math.min(this.getspeed() * 1.7, maxspeed));
            } else {
                // Уменьшаем скорость на 20%, но не менее чем на минимальное значение
                newAnimal.setspeed(Math.max(this.getspeed() * 0.8, minspeed));
            }

            simulation.addAnimal(newAnimal);
            foodCount = 0; // Сбрасываем счетчик пищи
        }
    }

}
