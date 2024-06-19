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
    private Random random = new Random();
    private Pane root = new Pane();

    @Override
    public void start(Stage primaryStage) {
        initializeAnimals(10);  // Инициализация 10 животных
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
            Animal animal = new Animal(random.nextDouble() * 800, random.nextDouble() * 600, 100);
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
                    double distance = Math.sqrt(Math.pow(animal.getCenterX() - food.getCenterX(), 2) +
                            Math.pow(animal.getCenterY() - food.getCenterY(), 2));
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestFood = food;
                    }
                }

                if (closestFood != null) {
                    animal.moveTowards(closestFood);
                    if (animal.isFoodFound(closestFood)) {
                        animal.setEnergy(animal.getEnergy() + 50);  // Животное получает энергию
                        foods.remove(closestFood);
                        root.getChildren().remove(closestFood);
                        spawnFood(1);  // Спауним новую пищу
                    } else {
                        animal.setEnergy(animal.getEnergy() - 0.1);  // Животное теряет энергию
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
