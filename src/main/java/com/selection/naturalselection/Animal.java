package com.selection.naturalselection;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Animal extends ImageView {
    private double energy;
    private double size;
    private double minsize;
    private double maxsize;
    private double speed;
    private double maxspeed;
    private double minspeed;
    private double interactionRadius;
    private double maxInteractionRadius;
    private double minInteractionRadius;
    private static final Random random = new Random();
    private int foodCount = 0;
    private Simulation simulation;

    public Animal(double x, double y, double energy, Simulation simulation) {
        super(new Image(Animal.class.getResourceAsStream("/com/selection/naturalselection/spore.png"))); // Путь к твоему изображению
        this.simulation = simulation;
        this.setX(x);
        this.setY(y);
        this.energy = energy;

        this.speed = 0.5 + random.nextDouble();
        this.maxspeed = this.speed * 3;
        this.minspeed = this.speed / 3;
        this.size = 20 + random.nextDouble();
        this.maxsize = this.size * 3;
        this.minsize = this.size / 3;

        this.interactionRadius = 15 + random.nextInt(5);
        this.maxInteractionRadius = this.interactionRadius * 2;
        this.minInteractionRadius = this.interactionRadius / 2;

        updateImageSize();
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
        return speed / Math.sqrt(size / 10); // Новая формула штрафа к скорости
    }

    public double getMaxSpeed() {
        return maxspeed;
    }

    public double getMinSpeed() {
        return minspeed;
    }

    public double getSize() {
        return size;
    }

    public double getMaxSize() {
        return maxsize;
    }

    public double getMinSize() {
        return minsize;
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

    public void setSize(double size) {
        this.size = size;
        updateImageSize();
    }

    public void setMaxSize(double maxsize) {
        this.maxsize = maxsize;
    }

    public void setMinSize(double minsize) {
        this.minsize = minsize;
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

        if (distance < size) {
            double overlap = size - distance;
            this.setX(this.getX() + dx / distance * overlap / 2);
            this.setY(this.getY() + dy / distance * overlap / 2);
            other.setX(other.getX() - dx / distance * overlap / 2);
            other.setY(other.getY() - dy / distance * overlap / 2);
        }

        // Новая логика: если одно животное больше другого на 40%, оно съедает более мелкое животное
        if (this.size >= other.size * 1.4) {
            this.energy += other.energy; // Поглощаем энергию съеденного животного
            simulation.removeAnimal(other);
        } else if (other.size >= this.size * 1.4) {
            other.energy += this.energy; // Поглощаем энергию съеденного животного
            simulation.removeAnimal(this);
        }
    }

    public void incrementFoodCount() {
        foodCount++;

        if (foodCount >= 3) {
            // Создание нового животного такого же цвета
            Animal newAnimal = new Animal(this.getX(), this.getY(), 10, simulation);
            newAnimal.setEffect(this.getEffect());

            // Шанс изменения скорости нового животного
            if (random.nextDouble() < 0.60) { // Например, 50% шанс
                newAnimal.setSpeed(Math.min(this.getSpeed() * 1.2, maxspeed));
            } else {
                // Уменьшаем скорость на 20%, но не менее чем на минимальное значение
                newAnimal.setSpeed(Math.max(this.getSpeed() * 0.8, minspeed));
            }

            if (random.nextDouble() < 0.60) { // Например, 50% шанс
                newAnimal.setSize(Math.min(this.getSize() * 1.5, maxsize));
            } else {
                newAnimal.setSize(Math.max(this.getSize() * 0.8, minsize));
            }

            // Шанс изменения радиуса взаимодействия нового животного
            if (random.nextDouble() < 0.60) { // 50% шанс
                newAnimal.setInteractionRadius(Math.min(this.getInteractionRadius() * 1.2, maxInteractionRadius));
            } else {
                // Уменьшаем радиус взаимодействия на 20%, но не менее чем на минимальное значение
                newAnimal.setInteractionRadius(Math.max(this.getInteractionRadius() * 0.8, minInteractionRadius));
            }

            simulation.addAnimal(newAnimal);
            foodCount = 0; // Сбрасываем счетчик пищи
        }
    }

    private void updateImageSize() {
        this.setFitWidth(size);
        this.setFitHeight(size);
    }
}
