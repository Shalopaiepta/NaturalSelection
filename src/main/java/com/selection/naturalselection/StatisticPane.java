package com.selection.naturalselection;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
//Объявление класса и элементов интерфейса
public class StatisticPane extends VBox {
    private Label titleLabel;
    private Label energyDepletionDeathsLabel;
    private Label predationDeathsLabel;
    private Label birthsLabel;
    private Label currentAnimalsLabel;
    private Label maxAnimalSizeLabel;
    private Label minAnimalSizeLabel;
    private Label maxAnimalSpeedLabel;
    private Label minAnimalSpeedLabel;
    private Label maxAnimalRadiusLabel;
    private Label minAnimalRadiusLabel;
    private Label SimulationEditionLabel;
    private Label FoodSpawnLabel;
    private Label TimeSpawnLabel;
    private TextField foodSpawnTextField;
    private TextField TimeSpawnTextField;
    private Label AnimalSpawnLabel;
    public TextField SpeedSpawnTextField;
    public TextField SizeSpawnTextField;
    public TextField VizionSpawnTextField;
    private Label AnimalSpawnLabelN2;
    public Button PauseButton;
    public Button SpawnAnimalButton;
    private Simulation simulation;

    //Создание элементов интерфейса и части экрана под статистику
    public StatisticPane() {
        this.simulation = simulation;
        titleLabel = new Label("Статистика");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.DARKBLUE);


        energyDepletionDeathsLabel = new Label("Умерли от голода: 0   ");
        energyDepletionDeathsLabel.setFont(Font.font("Arial", FontPosture.REGULAR, 14));

        AnimalSpawnLabel = new Label("Создание животного с параметрами:");
        AnimalSpawnLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        AnimalSpawnLabel.setTextFill(Color.DARKBLUE);

        AnimalSpawnLabelN2 = new Label("    Размер              Cкорость            Зрение");
        AnimalSpawnLabelN2.setFont(Font.font("Arial", FontPosture.REGULAR, 12));

        predationDeathsLabel = new Label("Съедено хищниками: 0");
        predationDeathsLabel.setFont(Font.font("Arial", FontPosture.REGULAR, 14));

        birthsLabel = new Label("Родилось клеток: 0    ");
        birthsLabel.setFont(Font.font("Arial", FontPosture.REGULAR, 14));

        currentAnimalsLabel = new Label("Клеток в симуляции: 0");
        currentAnimalsLabel.setFont(Font.font("Arial", FontPosture.REGULAR, 14));

        maxAnimalSizeLabel = new Label("Максимальный размер животного: 0");
        maxAnimalSizeLabel.setFont(Font.font("Arial", FontPosture.REGULAR, 14));

        minAnimalSizeLabel = new Label("Минимальный размер животного: 0");
        minAnimalSizeLabel.setFont(Font.font("Arial", FontPosture.REGULAR, 14));

        maxAnimalSpeedLabel = new Label("Максимальная скорость животного: 0");
        maxAnimalSpeedLabel.setFont(Font.font("Arial", FontPosture.REGULAR, 14));

        minAnimalSpeedLabel = new Label("Минимальная скорость животного: 0");
        minAnimalSpeedLabel.setFont(Font.font("Arial", FontPosture.REGULAR, 14));

        maxAnimalRadiusLabel = new Label("Максимальный радиус зрения: 0");
        maxAnimalRadiusLabel.setFont(Font.font("Arial", FontPosture.REGULAR, 14));

        minAnimalRadiusLabel = new Label("Минимальный радиус зрения: 0");
        minAnimalRadiusLabel.setFont(Font.font("Arial", FontPosture.REGULAR, 14));
        FoodSpawnLabel = new Label("Количество появляющейся еды");
        FoodSpawnLabel.setFont(Font.font("Arial", FontPosture.REGULAR, 12));



        foodSpawnTextField = new TextField("15");
        foodSpawnTextField.setFont(Font.font("Arial", FontPosture.REGULAR, 14));

        foodSpawnTextField.setPrefWidth(80);
        foodSpawnTextField.setMaxWidth(80);

        SpeedSpawnTextField = new TextField("15");
        SpeedSpawnTextField.setFont(Font.font("Arial", FontPosture.REGULAR, 14));
        SpeedSpawnTextField.setPrefWidth(80);
        SpeedSpawnTextField.setMaxWidth(80);

        SizeSpawnTextField = new TextField("15");
        SizeSpawnTextField.setFont(Font.font("Arial", FontPosture.REGULAR, 14));
        SizeSpawnTextField.setPrefWidth(80);
        SizeSpawnTextField.setMaxWidth(80);

        VizionSpawnTextField = new TextField("15");
        VizionSpawnTextField.setFont(Font.font("Arial", FontPosture.REGULAR, 14));
        VizionSpawnTextField.setPrefWidth(80);
        VizionSpawnTextField.setMaxWidth(80);

        SimulationEditionLabel = new Label("Настройки симуляции");
        SimulationEditionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        SimulationEditionLabel.setTextFill(Color.DARKBLUE);

        TimeSpawnLabel = new Label("Кол-во секунд до новой еды");
        TimeSpawnLabel.setFont(Font.font("Arial", FontPosture.REGULAR, 12));
        TimeSpawnTextField = new TextField("15");
        TimeSpawnTextField.setFont(Font.font("Arial", FontPosture.REGULAR, 14));
        TimeSpawnTextField.setPrefWidth(80);
        TimeSpawnTextField.setMaxWidth(80);
        SpawnAnimalButton = new Button("Создать животное");
        SpawnAnimalButton.setStyle("-fx-background-color: #0A67A3; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-border-color: transparent; " +
                "-fx-border-radius: 5px;");

        PauseButton = new Button("Пауза симуляции");
        PauseButton.setStyle("-fx-background-color: #FF6347; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-border-color: transparent; " +
                "-fx-border-radius: 5px;");
        HBox BoxN2 = new HBox(TimeSpawnLabel, TimeSpawnTextField);
        BoxN2.setSpacing(10);

        HBox foodSpawnBox = new HBox(FoodSpawnLabel, foodSpawnTextField);
        foodSpawnBox.setSpacing(10);

        HBox BoxN3 = new HBox(energyDepletionDeathsLabel, predationDeathsLabel);
        BoxN3.setSpacing(10);

        HBox BoxN4 = new HBox(birthsLabel,  currentAnimalsLabel);
        BoxN4.setSpacing(10);

        HBox BoxN5 = new HBox(AnimalSpawnLabel);
        BoxN5.setSpacing(10);

        HBox BoxN6 = new HBox(SizeSpawnTextField,SpeedSpawnTextField,  VizionSpawnTextField);
        BoxN6.setSpacing(10);

        Line separator1 = createSeparator(Color.DARKBLUE);
        Line separator2 = createSeparator(Color.DARKBLUE);
        Line separator3 = createSeparator(Color.DARKBLUE);
        Line separator4 = createSeparator(Color.DARKBLUE);
        Line separator5 = createSeparator(Color.DARKBLUE);
        Line separator6 = createSeparator(Color.DARKBLUE);
        Line separator7 = createSeparator(Color.DARKBLUE);

        this.getChildren().addAll(
                titleLabel, separator1, BoxN3, BoxN4, separator2, maxAnimalSizeLabel, minAnimalSizeLabel, separator3,
                maxAnimalSpeedLabel, minAnimalSpeedLabel, separator4, maxAnimalRadiusLabel, minAnimalRadiusLabel,
                separator5, SimulationEditionLabel,separator6,foodSpawnBox,BoxN2,separator7,BoxN5,BoxN6,AnimalSpawnLabelN2,SpawnAnimalButton,PauseButton
        );

        this.setStyle("-fx-background-color: lightgray; -fx-padding: 10; -fx-spacing: 10;");

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.4, 0.4));

        this.setEffect(dropShadow);
    }
//Метод создания разделительных линий
    private Line createSeparator(Color color) {
        Line separator = new Line(0, 0, 400, 0);
        separator.setStroke(color);
        separator.setStrokeWidth(2);
        return separator;
    }
//Метод для получения значения количества пищи, которое ввел пользователь
    public TextField getFoodSpawnAmount() {
        String text = foodSpawnTextField.getText().trim();
        if (text.isEmpty()) {
            foodSpawnTextField.setText("0");
            return foodSpawnTextField;
        }
        try {
            int amount = Integer.parseInt(text); // Пытаемся преобразовать текст в число
            if (amount < 150) {
                return foodSpawnTextField;
            } else {
                foodSpawnTextField.setText("15");
                return foodSpawnTextField;
            }
        } catch (NumberFormatException e) {
            foodSpawnTextField.setText("15");
            return foodSpawnTextField;
        }

    }



//метод для получения показателя скорости, которое ввел пользователь
    public TextField getSpeedSpawnTextField() {
        String text = SpeedSpawnTextField.getText().trim();
        if (text.isEmpty()) {
            SpeedSpawnTextField.setText("1");
            return SpeedSpawnTextField;
        }
        try {
            int amount = Integer.parseInt(text); // Пытаемся преобразовать текст в число
            if (amount < 10) {
                return SpeedSpawnTextField;
            } else {
                SpeedSpawnTextField.setText("1");
                return SpeedSpawnTextField;
            }
        } catch (NumberFormatException e) {
            SpeedSpawnTextField.setText("1");
            return SpeedSpawnTextField;
        }

    }
    //Метод для получения размера животного, который ввел пользователь
    public TextField getSizeSpawnTextField() {
        String text = SizeSpawnTextField.getText().trim();
        if (text.isEmpty()) {
            SizeSpawnTextField.setText("20");
            return SizeSpawnTextField;
        }
        try {
            int amount = Integer.parseInt(text); // Пытаемся преобразовать текст в число
            if (amount < 50) {
                return SizeSpawnTextField;
            } else {
                SizeSpawnTextField.setText("20");
                return SizeSpawnTextField;
            }
        } catch (NumberFormatException e) {
            SizeSpawnTextField.setText("20");
            return SizeSpawnTextField;
        }

    }
    //Метод получения времени до новой пищи, которое ввел пользователь
    public TextField getTimeSpawnTextField() {
        String text = TimeSpawnTextField.getText().trim();
        if (text.isEmpty()) {
            TimeSpawnTextField.setText("0");
            return TimeSpawnTextField;
        }
        try {
            int amount = Integer.parseInt(text); // Пытаемся преобразовать текст в число
            if (amount < 150) {
                return TimeSpawnTextField;
            } else {
                TimeSpawnTextField.setText("15");
                return TimeSpawnTextField;
            }
        } catch (NumberFormatException e) {
            TimeSpawnTextField.setText("15");
            return TimeSpawnTextField;
        }

    }
    //Метод получения радиуса зрения, которое ввел пользователь
    public TextField getVizionSpawnTextField() {
        String text = VizionSpawnTextField.getText().trim();
        if (text.isEmpty()) {
            VizionSpawnTextField.setText("100");
            return VizionSpawnTextField;
        }
        try {
            int amount = Integer.parseInt(text); // Пытаемся преобразовать текст в число
            if (amount < 300) {
                return VizionSpawnTextField;
            } else {
                VizionSpawnTextField.setText("100");
                return VizionSpawnTextField;
            }
        } catch (NumberFormatException e) {
            VizionSpawnTextField.setText("100");
            return VizionSpawnTextField;
        }

    }




    public int getTimeSpawnAmount() {
        String text = TimeSpawnTextField.getText().trim();

            int amount = Integer.parseInt(text); // Пытаемся преобразовать текст в число
                return amount; // Возвращаем число, если оно меньше 150
    }


    public void updateEnergyDepletionDeaths(int count) {
        energyDepletionDeathsLabel.setText("Умерли от голода: " + count);
    }

    public void updatePredationDeaths(int count) {
        predationDeathsLabel.setText("   Съедено хищниками: " + count);
    }

    public void updateMaxAnimalSize(double size) {
        maxAnimalSizeLabel.setText("Максимальный размер животного: " + size);
    }

    public void updateMinAnimalSize(double size) {
        minAnimalSizeLabel.setText("Минимальный размер животного: " + size);
    }

    public void updateMaxAnimalSpeed(double speed) {
        maxAnimalSpeedLabel.setText("Максимальная скорость животного: " + speed);
    }

    public void updateMinAnimalSpeed(double speed) {
        minAnimalSpeedLabel.setText("Минимальная скорость животного: " + speed);
    }

    public void updateMaxAnimalRadius(double radius) {
        maxAnimalRadiusLabel.setText("Максимальный радиус зрения: " + radius);
    }

    public void updateMinAnimalRadius(double radius) {
        minAnimalRadiusLabel.setText("Минимальный радиус зрения: " + radius);
    }

    public void updateNewAnimals(int count) {
        birthsLabel.setText("Родилось клеток: " + count);
    }

    public void updateCurrentAnimals(int count) {
        currentAnimalsLabel.setText("        Клеток в симуляции: " + count);
    }
}
