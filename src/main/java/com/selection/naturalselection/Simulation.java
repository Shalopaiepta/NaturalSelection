package com.selection.naturalselection;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Simulation extends Application {

    private List<Animal> animals = new ArrayList<>();
    private List<Food> foods = new ArrayList<>();
    private List<Animal> newAnimals = new ArrayList<>();
    private Random random = new Random();
    private Pane root = new Pane();

    private static final double SCREEN_WIDTH = 800;
    private static final double SCREEN_HEIGHT = 600;
    private static final double BORDER_MARGIN = 50; // Маржа от границы экрана, внутри которой еда не будет спавниться

    @Override
    public void start(Stage primaryStage) {
        // Установка фото-фона
        ImageView background = createBackground("C:\\Users\\Lenovo\\IdeaProjects\\NaturalSelection\\NaturalSelection\\src\\main\\resources\\com\\selection\\naturalselection\\fon.png");
        root.getChildren().add(background);

        initializeAnimals(10);  // Инициализация 10 животных
        spawnFood(20);  // Спаун 20 единиц пищи

        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
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

        // Таймер для спауна 15 единиц пищи каждые 20 секунд
        Timeline foodSpawnTimer = new Timeline(new KeyFrame(Duration.seconds(20), event -> spawnFood(15)));
        foodSpawnTimer.setCycleCount(Timeline.INDEFINITE);
        foodSpawnTimer.play();
    }

    private ImageView createBackground(String path) {
        Image image = new Image(new File(path).toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(SCREEN_WIDTH);
        imageView.setFitHeight(SCREEN_HEIGHT);
        imageView.setPreserveRatio(false);
        return imageView;
    }

    private void initializeAnimals(int count) {
        for (int i = 0; i < count; i++) {
            Animal animal = new Animal(random.nextDouble() * SCREEN_WIDTH, random.nextDouble() * SCREEN_HEIGHT, 20, this);
            animals.add(animal);
            root.getChildren().add(animal);
        }
    }

    private void spawnFood(int count) {
        for (int i = 0; i < count; i++) {
            double x = BORDER_MARGIN + random.nextDouble() * (SCREEN_WIDTH - 2 * BORDER_MARGIN);
            double y = BORDER_MARGIN + random.nextDouble() * (SCREEN_HEIGHT - 2 * BORDER_MARGIN);
            Food food = new Food(x, y);
            foods.add(food);
            root.getChildren().add(food);
        }
    }

    private void updateSimulation() {
        List<Animal> animalsToRemove = new ArrayList<>();
        List<Animal> animalsToAdd = new ArrayList<>();

        Iterator<Animal> iterator = animals.iterator();
        while (iterator.hasNext()) {
            Animal animal = iterator.next();
            if (animal.getEnergy() > 0) {
                // Поиск пищи в пределах радиуса взаимодействия
                Food closestFood = null;
                double closestDistance = Double.MAX_VALUE;
                for (Food food : foods) {
                    double distance = Math.sqrt(Math.pow(animal.getX() - food.getCenterX(), 2) +
                            Math.pow(animal.getY() - food.getCenterY(), 2));
                    if (distance < animal.getInteractionRadius() && distance < closestDistance) {
                        closestDistance = distance;
                        closestFood = food;
                    }
                }

                if (closestFood != null) {
                    animal.moveTowards(closestFood);
                    if (animal.isInContactWithFood(closestFood)) {
                        animal.setEnergy(animal.getEnergy() + 30);  // Животное получает энергию
                        animal.incrementFoodCount(); // Увеличиваем счетчик пищи

                        // Удаление пищи
                        foods.remove(closestFood);
                        root.getChildren().remove(closestFood);
                    } else {
                        animal.setEnergy(animal.getEnergy() - 0.04);  // Животное теряет энергию
                    }
                } else {
                    animal.moveRandomly();
                    animal.setEnergy(animal.getEnergy() - 0.04);  // Животное теряет энергию
                }
            } else {
                // Животное умирает и превращается в единицу пищи
                Food newFood = new Food(animal.getX(), animal.getY());
                newFood.setColor(Color.BLACK);  // Устанавливаем цвет пищи черным
                foods.add(newFood);
                root.getChildren().add(newFood);
                animalsToRemove.add(animal);  // Отмечаем животное для удаления
            }
        }

        // Удаляем отмеченные для удаления животные
        for (Animal animal : animalsToRemove) {
            animals.remove(animal);
            root.getChildren().remove(animal);
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
    }

    public void removeFood(Food food) {
        foods.remove(food);
        root.getChildren().remove(food);
    }

    public List<Food> getFood() {
        return foods;
    }
    public void addAnimal(Animal animal) {
        // Смещение по осям x и y для нового животного
        double offsetX = random.nextDouble() * 40 - 20; // случайное смещение от -20 до 20 по оси x
        double offsetY = random.nextDouble() * 40 - 20; // случайное смещение от -20 до 20 по оси y

        animal.setX(animal.getX() + offsetX);
        animal.setY(animal.getY() + offsetY);

        newAnimals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
        root.getChildren().remove(animal);
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
