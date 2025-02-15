package com.selection.naturalselection;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation extends Application {
    private double speedMultiplier = 1.0;
    private Timeline foodSpawnTimer; // Таймер для спауна пищи
    private List<Animal> animals = new ArrayList<>();
    private List<Food> foods = new ArrayList<>();
    private List<Animal> newAnimals = new ArrayList<>();
    private Random random = new Random();
    private Pane simulationPane = new Pane();
    private StatisticPane statisticPane = new StatisticPane();
    private boolean isPaused = false;

    private static final double SIMULATION_WIDTH = 700;
    private static final double SIMULATION_HEIGHT = 700;
    private static final double BORDER_MARGIN = 50; // Маржа от границы экрана, внутри которой еда не будет спавниться
    private static final double STATISTIC_PANE_WIDTH = 200; // Ширина панели статистики
    private static final double ANIMAL_SPAWN_MARGIN = STATISTIC_PANE_WIDTH + BORDER_MARGIN; // Маржа для спавна животных



    @Override
    public void start(Stage primaryStage) {
        // Установка фото-фона
        ImageView background = createBackground("C:\\Users\\Lenovo\\IdeaProjects\\NaturalSelection\\src\\main\\resources\\com\\selection\\naturalselection\\fon.png");
        simulationPane.getChildren().add(background);

        initializeAnimals(10, simulationPane); // Инициализация 10 животных
        spawnFood(20, simulationPane); // Спаун 20 единиц пищи

        // Create the root layout
        HBox root = new HBox();
        root.getChildren().addAll(simulationPane, statisticPane);

        // Create the scene
        Scene scene = new Scene(root, 1100,700);
        primaryStage.setTitle("Natural Selection Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Обработчик изменения времени до новой пищи
        statisticPane.getTimeSpawnTextField().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isPaused) {
                initializeFoodSpawnTimer(); // Переинициализация таймера при изменении значения, только если не на паузе
            }
        });

        statisticPane.getFoodSpawnAmount().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isPaused) {
                initializeFoodSpawnTimer(); // Переинициализация таймера при изменении значения, только если не на паузе
            }
        });

        // Инициализация таймера для спауна пищи
        if(!isPaused) {
            initializeFoodSpawnTimer();

            // Создание и запуск таймера обновления симуляции
            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (!isPaused) {
                        // Логика обновления симуляции
                        updateSimulation(simulationPane, statisticPane);
                    }
                }
            };

            timer.start();

            // Обработчик кнопки паузы/возобновления симуляции
            statisticPane.PauseButton.setOnAction(event -> {
                isPaused = !isPaused;
                if (isPaused) {
                    foodSpawnTimer.pause(); // При паузе останавливаем таймер спавна пищи
                    timer.stop(); // Останавливаем таймер обновления симуляции
                    statisticPane.PauseButton.setText("Возобновить симуляцию");
                } else {
                    foodSpawnTimer.play(); // При возобновлении запускаем таймер спавна пищи
                    timer.start(); // Запускаем таймер обновления симуляции
                    statisticPane.PauseButton.setText("Пауза симуляции");
                }
            });
        }

        statisticPane.SpawnAnimalButton.setOnAction(event -> {
            // Генерация случайной позиции для нового животного внутри simulationPane
            double x = Math.random() * 690;
            double y = Math.random() * 690;

            // Получение значений параметров из текстовых полей
            int size = Integer.parseInt(statisticPane.getSizeSpawnTextField().getText().trim());
            int speed = Integer.parseInt(statisticPane.getSpeedSpawnTextField().getText().trim());
            int visionRadius = Integer.parseInt(statisticPane.getVizionSpawnTextField().getText().trim());

            // Создание нового животного с этими параметрами
            Animal newAnimal = new Animal(x, y, 20, this);
            newAnimal.setSpeed(speed);
            newAnimal.setSize(size);
            newAnimal.setInteractionRadius(visionRadius);

            // Добавление нового животного в симуляцию
            addAnimal(newAnimal);
        });



}
    private void initializeFoodSpawnTimer() {
        int timeSpawnAmount = statisticPane.getTimeSpawnAmount();
        if(!isPaused) {
                if (foodSpawnTimer != null) {
                    foodSpawnTimer.stop(); // Останавливаем текущий таймер, если он существует
                }


                String text = statisticPane.getFoodSpawnAmount().getText().trim();
                int FoodSpawnAmount = Integer.parseInt(text);// Получаем текущее значение из текстового поля
                foodSpawnTimer = new Timeline(
                        new KeyFrame(Duration.seconds(timeSpawnAmount), event -> spawnFood(FoodSpawnAmount, simulationPane))
                );
                foodSpawnTimer.setCycleCount(Timeline.INDEFINITE);
                foodSpawnTimer.play();
            }

    }
    private ImageView createBackground(String path) {
        Image image = new Image(new File(path).toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(SIMULATION_WIDTH);
        imageView.setFitHeight(SIMULATION_HEIGHT);
        imageView.setPreserveRatio(false);
        return imageView;
    }

    private void initializeAnimals(int count, Pane simulationPane) {
        for (int i = 0; i < count; i++) {
            double x = BORDER_MARGIN + random.nextDouble() * (SIMULATION_WIDTH - ANIMAL_SPAWN_MARGIN);
            double y = BORDER_MARGIN + random.nextDouble() * (SIMULATION_HEIGHT - 2 * BORDER_MARGIN);
            Animal animal = new Animal(x, y, 20, this);
            animals.add(animal);
            simulationPane.getChildren().add(animal);
        }
    }

    private void spawnFood(int count, Pane simulationPane) {
        for (int i = 0; i < count; i++) {
            double x = BORDER_MARGIN + random.nextDouble() * (SIMULATION_WIDTH - 2 * BORDER_MARGIN);
            double y = BORDER_MARGIN + random.nextDouble() * (SIMULATION_HEIGHT - 2 * BORDER_MARGIN);
            Food food = new Food(x, y);
            foods.add(food);
            simulationPane.getChildren().add(food);
        }
    }

    public double getMaxAnimalSize() {
        if (animals.isEmpty()) return 0;
        double maxSize = animals.get(0).getSize();
        for (Animal animal : animals) {
            if (animal.getSize() > maxSize) {
                maxSize = animal.getSize();
            }
        }
        return maxSize;
    }

    public double getMinAnimalSize() {
        if (animals.isEmpty()) return 0;
        double minSize = animals.get(0).getSize();
        for (Animal animal : animals) {
            if (animal.getSize() < minSize) {
                minSize = animal.getSize();
            }
        }
        return minSize;
    }

    public double getMaxAnimalSpeed() {
        if (animals.isEmpty()) return 0;
        double maxSpeed = animals.get(0).getSpeed();
        for (Animal animal : animals) {
            if (animal.getSpeed() > maxSpeed) {
                maxSpeed = animal.getSpeed();
            }
        }
        return maxSpeed;
    }

    public double getMinAnimalSpeed() {
        if (animals.isEmpty()) return 0;
        double minSpeed = animals.get(0).getSpeed();
        for (Animal animal : animals) {
            if (animal.getSpeed() < minSpeed) {
                minSpeed = animal.getSpeed();
            }
        }
        return minSpeed;
    }

    public double getMaxAnimalRadius() {
        if (animals.isEmpty()) return 0;
        double maxRadius = animals.get(0).getInteractionRadius();
        for (Animal animal : animals) {
            if (animal.getInteractionRadius() > maxRadius) {
                maxRadius = animal.getInteractionRadius();
            }
        }
        return maxRadius;
    }

    public double getMinAnimalRadius() {
        if (animals.isEmpty()) return 0;
        double minRadius = animals.get(0).getInteractionRadius();
        for (Animal animal : animals) {
            if (animal.getInteractionRadius() < minRadius) {
                minRadius = animal.getInteractionRadius();
            }
        }
        return minRadius;
    }

    public double maxSize = 0;
    public double minSize = 0;
    public double maxSpeed = 0;
    public double minSpeed = 0;
    public double maxRadius = 0;
    public double minRadius = 0;
    public int energyDepletionDeaths = 0;
    public int predationDeaths = 0;
    public int newanimals = 0;

    private void updateSimulation(Pane simulationPane, StatisticPane statisticPane) {
        List<Animal> animalsToRemove = new ArrayList<>();
        maxSize = getMaxAnimalSize();
        minSize = getMinAnimalSize();
        maxSpeed = getMaxAnimalSpeed();
        minSpeed = getMinAnimalSpeed();
        maxRadius = getMaxAnimalRadius();
        minRadius = getMinAnimalRadius();

        statisticPane.updateEnergyDepletionDeaths(energyDepletionDeaths);
        statisticPane.updatePredationDeaths(predationDeaths);
        statisticPane.updateNewAnimals(newanimals);
        statisticPane.updateCurrentAnimals(animals.size());
        statisticPane.updateMaxAnimalSize(maxSize);
        statisticPane.updateMinAnimalSize(minSize);
        statisticPane.updateMaxAnimalSpeed(maxSpeed);
        statisticPane.updateMinAnimalSpeed(minSpeed);
        statisticPane.updateMaxAnimalRadius(maxRadius);
        statisticPane.updateMinAnimalRadius(minRadius);

        for (Animal animal : animals) {
            if (animal.getEnergy() > 0) {
                animal.moveRandomly();
                animal.setEnergy(animal.getEnergy() - 0.04); // Животное теряет энергию
            } else {
                energyDepletionDeaths++; // Увеличиваем счетчик смертей от недостатка энергии
                // Животное умирает и превращается в единицу пищи
                Food newFood = new Food(animal.getX(), animal.getY());
                newFood.setColor(Color.BLACK); // Устанавливаем цвет пищи черным
                foods.add(newFood);
                simulationPane.getChildren().add(newFood);
                animalsToRemove.add(animal); // Отмечаем животное для удаления
            }
        }

        // Удаляем отмеченные для удаления животные
        for (Animal animal : animalsToRemove) {
            animals.remove(animal);
            simulationPane.getChildren().remove(animal);
        }

        // Добавление новых животных после итерации
        if (!newAnimals.isEmpty()) {
            animals.addAll(newAnimals);
            newanimals += 1;
            simulationPane.getChildren().addAll(newAnimals);
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
        simulationPane.getChildren().remove(food);
    }

    public List<Food> getFood() {
        return foods;
    }

    public void addAnimal(Animal animal) {
        // Смещение по осям x и y для нового животного
        double offsetX = random.nextDouble() * 40 - 20; // случайное смещение от -20 до 20 по оси x
        double offsetY = random.nextDouble() * 40 - 20; // случайное смещение от -20 до 20 по оси y

        double newX = animal.getX() + offsetX;
        double newY = animal.getY() + offsetY;

        // Проверка на границы спавна животных
        if (newX < BORDER_MARGIN) {
            newX = BORDER_MARGIN;
        } else if (newX > SIMULATION_WIDTH - ANIMAL_SPAWN_MARGIN) {
            newX = SIMULATION_WIDTH - ANIMAL_SPAWN_MARGIN;
        }

        if (newY < BORDER_MARGIN) {
            newY = BORDER_MARGIN;
        } else if (newY > SIMULATION_HEIGHT - BORDER_MARGIN) {
            newY = SIMULATION_HEIGHT - BORDER_MARGIN;
        }

        animal.setX(newX);
        animal.setY(newY);

        newAnimals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
        simulationPane.getChildren().remove(animal);
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public static void main(String[] args) {
        launch(args);
    }

}