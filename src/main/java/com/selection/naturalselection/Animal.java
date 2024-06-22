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
    private double interactionRadius;
    private double maxInteractionRadius;
    private double minInteractionRadius;
    private static final double ANIMAL_SIZE = 20;
    private static final Random random = new Random();
    private int foodCount = 0;

    public Animal(double x, double y, double energy) {
        super(new Image(Animal.class.getResourceAsStream("/com/selection/naturalselection/spore.png"))); // Путь к твоему изображению
        this.setX(x);
        this.setY(y);
        this.energy = energy;

        this.speed = 0.5 + random.nextDouble();
        this.maxspeed = this.speed * 3;
        this.minspeed = this.speed / 3;

        this.interactionRadius = 15 + random.nextInt(5);
        this.maxInteractionRadius = this.interactionRadius * 2;
        this.minInteractionRadius = this.interactionRadius / 2;

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

    public double getSpeed() {
        return speed;
    }

    public double getMaxSpeed() {
        return maxspeed;
    }

    public double getMinSpeed() {
        return minspeed;
    }

    public double getInteractionRadius() {
        return interactionRadius;
    }

    public double getMaxInteractionRadius() {
        return maxInteractionRadius;
    }

    public double getMinInteractionRadius() {
        return minInteractionRadius;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setMaxSpeed(double maxspeed) {
        this.maxspeed = maxspeed;
    }

    public void setMinSpeed(double minspeed) {
        this.minspeed = minspeed;
    }

    public void setInteractionRadius(double interactionRadius) {
        this.interactionRadius = interactionRadius;
    }

    public void setMaxInteractionRadius(double maxInteractionRadius) {
        this.maxInteractionRadius = maxInteractionRadius;
    }

    public void setMinInteractionRadius(double minInteractionRadius) {
        this.minInteractionRadius = minInteractionRadius;
    }

    public void moveTowards(Food food) {
        // Реализация движения к пище
        double dx = food.getCenterX() - this.getX();
        double dy = food.getCenterY() - this.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 1) {
            this.setX(this.getX() + dx / distance * speed);
            this.setY(this.getY() + dy / distance * speed);
        }
    }

    public boolean isFoodFound(Food food) {
        // Проверка, достигло ли животное пищи
        double dx = food.getCenterX() - this.getX();
        double dy = food.getCenterY() - this.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance <= interactionRadius;
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
            if (random.nextDouble() < 0.75) { // Например, 75% шанс
                newAnimal.setSpeed(Math.min(this.getSpeed() * 1.7, maxspeed));
            } else {
                // Уменьшаем скорость на 20%, но не менее чем на минимальное значение
                newAnimal.setSpeed(Math.max(this.getSpeed() * 0.8, minspeed));
            }

            // Шанс изменения радиуса взаимодействия нового животного
            if (random.nextDouble() < 0.75) { // 75% шанс
                newAnimal.setInteractionRadius(Math.min(this.getInteractionRadius() * 1.5, maxInteractionRadius));
            } else {
                // Уменьшаем радиус взаимодействия на 20%, но не менее чем на минимальное значение
                newAnimal.setInteractionRadius(Math.max(this.getInteractionRadius() * 0.8, minInteractionRadius));
            }

            simulation.addAnimal(newAnimal);
            foodCount = 0; // Сбрасываем счетчик пищи
        }
    }
}
