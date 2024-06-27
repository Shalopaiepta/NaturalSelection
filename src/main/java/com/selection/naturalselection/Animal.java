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
    public int  energydeadcount=0;
    public int  predatordeadcount=0;
    private double moveDirectionX;
    private double moveDirectionY;
    private int moveTicks = 0;

    public Animal(double x, double y, double energy, Simulation simulation) {
        super(new Image(Animal.class.getResourceAsStream("/com/selection/naturalselection/spore.png")));
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
        applyRandomColor();
        setRandomDirection();
    }

    private void applyRandomColor() {
        double hue = random.nextDouble() * 360 - 180;
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(hue / 360.0);
        this.setEffect(colorAdjust);
    }

    public double getEnergy() {
        return energy;
    }

    public double getSpeed() {
        return speed / Math.sqrt(size / 10);
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
            foodCount = 0;
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

    public void moveTowards(Animal target) {
        double dx = target.getX() - this.getX();
        double dy = target.getY() - this.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 1) {
            dx /= distance;
            dy /= distance;
            this.setX(this.getX() + dx * getSpeed());
            this.setY(this.getY() + dy * getSpeed());
        }
    }

    public void moveAwayFrom(Animal threat) {
        double dx = this.getX() - threat.getX();
        double dy = this.getY() - threat.getY();
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

        Animal prey = findPrey();
        if (prey != null) {
            moveTowards(prey);
            if (isColliding(prey)) {
                resolveCollision(prey);
            }
        } else {
            Animal threat = findThreat();
            if (threat != null) {
                moveAwayFrom(threat);
            } else {
                Food food = findFood();
                if (food != null) {
                    moveTowards(food);
                    if (isInContactWithFood(food)) {
                        setEnergy(getEnergy() + 30); // Животное получает энергию
                        incrementFoodCount(); // Увеличиваем счетчик пищи
                        simulation.removeFood(food);
                    } else {
                        setEnergy(getEnergy() - 0.04); // Животное теряет энергию
                    }
                } else {
                    double newX = this.getX() + moveDirectionX * getSpeed();
                    double newY = this.getY() + moveDirectionY * getSpeed();

                    List<Animal> animals = simulation.getAnimals();
                    for (Animal other : animals) {
                        if (other != this && isColliding(newX, newY, other)) {
                            if (this.size > other.size * 1.4) {
                                continue;
                            }
                            setRandomDirection();
                            return;
                        }
                    }

                    this.setX(newX);
                    this.setY(newY);
                    moveTicks--;
                }
            }
        }
    }

    private Animal findPrey() {
        Animal closestPrey = null;
        double closestDistance = Double.MAX_VALUE;
        for (Animal other : simulation.getAnimals()) {
            if (other != this && this.size > other.size * 1.3) { // Проверка на 30% больше
                double distance = Math.sqrt(Math.pow(this.getX() - other.getX(), 2) + Math.pow(this.getY() - other.getY(), 2));
                if (distance < this.interactionRadius && distance < closestDistance) {
                    closestDistance = distance;
                    closestPrey = other;
                }
            }
        }
        return closestPrey;
    }

    private Animal findThreat() {
        Animal closestThreat = null;
        double closestDistance = Double.MAX_VALUE;
        for (Animal other : simulation.getAnimals()) {
            if (other != this && other.size > this.size * 1.3) { // Проверка на 30% больше
                double distance = Math.sqrt(Math.pow(this.getX() - other.getX(), 2) + Math.pow(this.getY() - other.getY(), 2));
                if (distance < this.interactionRadius && distance < closestDistance) {
                    closestDistance = distance;
                    closestThreat = other;
                }
            }
        }
        return closestThreat;
    }

    private Food findFood() {
        Food closestFood = null;
        double closestDistance = Double.MAX_VALUE;
        for (Food food : simulation.getFood()) {
            double distance = Math.sqrt(Math.pow(this.getX() - food.getCenterX(), 2) + Math.pow(this.getY() - food.getCenterY(), 2));
            if (distance < this.interactionRadius && distance < closestDistance) {
                closestDistance = distance;
                closestFood = food;
            }
        }
        return closestFood;
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
            angle = random.nextDouble() * 2 * Math.PI;
        }
        moveDirectionX = Math.cos(angle);
        moveDirectionY = Math.sin(angle);
        moveTicks = 100 + random.nextInt(100);
    }

    private double calculateBounceAngle() {
        double x = this.getX();
        double y = this.getY();
        double angle = random.nextDouble() * Math.PI;

        if (x <= 0 || x >= 800 - size) {
            moveDirectionX = -moveDirectionX;
            return Math.atan2(moveDirectionY, moveDirectionX);
        }
        if (y <= 0 || y >= 600 - size) {
            moveDirectionY = -moveDirectionY;
            return Math.atan2(moveDirectionY, moveDirectionX);
        }
        return angle;
    }

    private void updateImageSize() {
        this.setFitWidth(size);
        this.setFitHeight(size);
    }

    public boolean isInContactWithFood(Food food) {
        double dx = food.getCenterX() - this.getX();
        double dy = food.getCenterY() - this.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < this.size;
    }

    public boolean isColliding(Animal other) {
        double dx = other.getX() - this.getX();
        double dy = other.getY() - this.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < (this.size + other.size) / 2;
    }

    public boolean isColliding(double newX, double newY, Animal other) {
        double dx = other.getX() - newX;
        double dy = other.getY() - newY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < (this.size + other.size) / 2;
    }

    public void resolveCollision(Animal other) {
        if (this.size > other.size * 1.3) { // Проверка на 30% больше
            // Это животное съедает другое
            this.setEnergy(this.getEnergy() + other.getEnergy() / 5); // Поглощает половину энергии другого животного
            this.incrementFoodCount();
            predatordeadcount+=1;
            simulation.removeAnimal(other);
        } else {
            // Отталкивание
            double dx = other.getX() - this.getX();
            double dy = other.getY() - this.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance > 0) {
                dx /= distance;
                dy /= distance;
                this.setX(this.getX() - dx * this.getSpeed());
                this.setY(this.getY() - dy * this.getSpeed());
                other.setX(other.getX() + dx * other.getSpeed());
                other.setY(other.getY() + dy * other.getSpeed());
            }
        }
    }

    private void reproduce() {
        double newSpeed = this.speed * (0.9 + random.nextDouble() * 0.2); // Новый животное получает скорость в диапазоне от 90% до 110% от родительской
        double newSize = this.size * (0.6 + random.nextDouble() * 0.3); // Новый животное получает размер в диапазоне от 90% до 110% от родительской
        double newInteractionRadius = this.interactionRadius * (0.9 + random.nextDouble() * 0.4); // Новый животное получает радиус в диапазоне от 90% до 110% от родительского

        Animal newAnimal = new Animal(this.getX(), this.getY(), this.energy / 2, simulation);
        newAnimal.setSpeed(newSpeed);
        newAnimal.setSize(newSize);
        newAnimal.setInteractionRadius(newInteractionRadius);
        newAnimal.setEffect(this.getEffect()); // Наследование цвета родительского животного

        simulation.addAnimal(newAnimal);
    }
}
