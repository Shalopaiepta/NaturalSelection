package com.selection.naturalselection;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation extends Application {

    private List<Animal> animals = new ArrayList<>();
    private List<Food> foods = new ArrayList<>();
    private List<Animal> newAnimals = new ArrayList<>();
    private Random random = new Random();
    private Pane root = new Pane();

    @Override
    public void start(Stage primaryStage) {
        initializeAnimals(1);  // Инициализация 10 животных
        spawnFood(20);  // Спаун 20 единиц пищи

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Natural Selection Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateSimulation();
            }
        };
        timer.start();
    }

    private void initializeAnimals(int count) {
        for (int i = 0; i < count; i++) {
            Animal animal = new Animal(random.nextDouble() * 800, random.nextDouble() * 600, 10);
            animals.add(animal);
            root.getChildren().add(animal);
        }
    }

    private void spawnFood(int count) {
        for (int i = 0; i < count; i++) {
            Food food = new Food(random.nextDouble() * 800, random.nextDouble() * 600);
            foods.add(food);
            root.getChildren().add(food);
        }
    }

    private void updateSimulation() {
        for (Animal animal : animals) {
            if (animal.getEnergy() > 0) {
                // Поиск ближайшей пищи
                Food closestFood = null;
                double closestDistance = Double.MAX_VALUE;
                for (Food food : foods) {
                    double distance = Math.sqrt(Math.pow(animal.getX() - food.getCenterX(), 2) +
                            Math.pow(animal.getY() - food.getCenterY(), 2));
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestFood = food;
                    }
                }

                if (closestFood != null) {
                    animal.moveTowards(closestFood);
                    if (animal.isFoodFound(closestFood)) {
                        animal.setEnergy(animal.getEnergy() +10 );  // Животное получает энергию
                        animal.incrementFoodCount(this); // Увеличиваем счетчик пищи

                        // Удаление пищи
                        foods.remove(closestFood);
                        root.getChildren().remove(closestFood);
                    } else {
                        animal.setEnergy(animal.getEnergy() - 0.04);  // Животное теряет энергию
                    }
                }
            }
        }

        // Добавление новых животных после итерации
        if (!newAnimals.isEmpty()) {
            animals.addAll(newAnimals);
            root.getChildren().addAll(newAnimals);
            newAnimals.clear();
        }

        // Проверка коллизий между животными
        for (int i = 0; i < animals.size(); i++) {
            Animal animal1 = animals.get(i);
            for (int j = i + 1; j < animals.size(); j++) {
                Animal animal2 = animals.get(j);
                if (animal1.isColliding(animal2)) {
                    animal1.resolveCollision(animal2);
                }
            }
        }

        // Спауним новую пищу, если текущая закончилась
        if (foods.isEmpty()) {
            spawnFood(20);  // Количество единиц пищи для спауна можно изменить при необходимости
        }
    }

    public void addAnimal(Animal animal) {
        // Смещение по осям x и y для нового животного
        double offsetX = random.nextDouble() * 40 - 20; // случайное смещение от -20 до 20 по оси x
        double offsetY = random.nextDouble() * 40 - 20; // случайное смещение от -20 до 20 по оси y

        animal.setX(animal.getX() + offsetX);
        animal.setY(animal.getY() + offsetY);

        newAnimals.add(animal);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
