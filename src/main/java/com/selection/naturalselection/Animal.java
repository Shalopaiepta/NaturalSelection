package com.selection.naturalselection;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
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

    // Новые поля для случайного движения
    private double moveDirectionX;
    private double moveDirectionY;
    private int moveTicks = 0;

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

        this.interactionRadius = 100 + random.nextInt(5);
        this.maxInteractionRadius = this.interactionRadius * 2;
        this.minInteractionRadius = this.interactionRadius / 2;

        updateImageSize();
        // Применение случайного цвета
        applyRandomColor();
        // Инициализация случайного направления движения
        setRandomDirection();
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

    public void incrementFoodCount() {
        foodCount++;
        if (foodCount >= 2) {
            reproduce();
            foodCount = 0; // Сбросить счетчик после размножения
        }
    }

    public void moveTowards(Food food) {
        double dx = food.getCenterX() - this.getX();
        double dy = food.getCenterY() - this.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 1) {
            dx /= distance;
            dy /= distance;
            this.setX(this.getX() + dx * getSpeed());
            this.setY(this.getY() + dy * getSpeed());
        }
    }

    public void moveRandomly() {
        if (moveTicks <= 0 || isAtEdge()) {
            setRandomDirection();
        }

        double newX = this.getX() + moveDirectionX * getSpeed();
        double newY = this.getY() + moveDirectionY * getSpeed();

        // Проверка на коллизии с другими животными
        List<Animal> animals = simulation.getAnimals();
        for (Animal other : animals) {
            if (other != this && isColliding(newX, newY, other)) {
                // Если текущее животное больше другого достаточно, чтобы съесть его, продолжить движение
                if (this.size > other.size * 1.4) {
                    continue;
                }
                // Иначе изменить направление
                setRandomDirection();
                return;
            }
        }

        this.setX(newX);
        this.setY(newY);
        moveTicks--;
    }

    private boolean isAtEdge() {
        double x = this.getX();
        double y = this.getY();
        return x <= 0 || x >= 800 - size || y <= 0 || y >= 600 - size;
    }

    private void setRandomDirection() {
        double angle;
        if (isAtEdge()) {
            angle = calculateBounceAngle();
        } else {
            angle = random.nextDouble() * 2 * Math.PI; // Случайный угол в радианах
        }
        moveDirectionX = Math.cos(angle);
        moveDirectionY = Math.sin(angle);
        moveTicks = 100 + random.nextInt(100); // Количество тиков, в течение которых животное движется в этом направлении
    }

    private double calculateBounceAngle() {
        double x = this.getX();
        double y = this.getY();
        double angle = random.nextDouble() * Math.PI; // Полукруг (180 градусов) для отклонения от границы

        if (x <= 0 || x >= 800 - size) { // Левый или правый край
            moveDirectionX = -moveDirectionX; // Изменить направление по X
            return Math.atan2(moveDirectionY, moveDirectionX);
        }
        if (y <= 0 || y >= 600 - size) { // Верхний или нижний край
            moveDirectionY = -moveDirectionY; // Изменить направление по Y
            return Math.atan2(moveDirectionY, moveDirectionX);
        }
        return angle; // Вернуть случайный угол, если не было изменения направления
    }

    private void updateImageSize() {
        this.setFitWidth(size);
        this.setFitHeight(size);
    }

    public boolean isInContactWithFood(Food food) {
        double dx = food.getCenterX() - this.getX();
        double dy = food.getCenterY() - this.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < this.size / 2;
    }

    public boolean isColliding(Animal other) {
        double dx = other.getX() - this.getX();
        double dy = other.getY() - this.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < (this.size + other.size) / 2;
    }

    private boolean isColliding(double newX, double newY, Animal other) {
        double dx = other.getX() - newX;
        double dy = other.getY() - newY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < (this.size + other.size) / 2;
    }

    public void resolveCollision(Animal other) {
        if (this.size > other.size * 1.4) { // Животное должно быть как минимум на 40% больше другого
            this.energy += other.energy * 0.5; // Животное получает 50% энергии другого животного
            simulation.removeAnimal(other); // Удаляем другое животное из симуляции
        } else if (other.size > this.size * 1.4) {
            other.energy += this.energy * 0.5;
            simulation.removeAnimal(this);
        } else {
            // Отталкивание при коллизии
            double dx = this.getX() - other.getX();
            double dy = this.getY() - other.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance == 0) {
                // Избегаем деления на ноль
                distance = 0.01;
            }
            dx /= distance;
            dy /= distance;
            double overlap = (this.size + other.size) / 2 - distance;
            this.setX(this.getX() + dx * overlap / 2);
            this.setY(this.getY() + dy * overlap / 2);
            other.setX(other.getX() - dx * overlap / 2);
            other.setY(other.getY() - dy * overlap / 2);
        }
    }

            private void reproduce() {
        double newSpeed = this.speed * (0.9 + random.nextDouble() * 0.2); // Новый животное получает скорость в диапазоне от 90% до 110% от родительской
        double newSize = this.size * (0.9 + random.nextDouble() * 0.2); // Новый животное получает размер в диапазоне от 90% до 110% от родительской
        double newInteractionRadius = this.interactionRadius * (0.9 + random.nextDouble() * 0.2); // Новый животное получает радиус в диапазоне от 90% до 110% от родительского

        Animal newAnimal = new Animal(this.getX(), this.getY(), this.energy / 2, simulation);
        newAnimal.setSpeed(newSpeed);
        newAnimal.setSize(newSize);
        newAnimal.setInteractionRadius(newInteractionRadius);
        newAnimal.setEffect(this.getEffect()); // Наследование цвета родительского животного

        simulation.addAnimal(newAnimal);
    }
}
