package com.selection.naturalselection;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

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

    public StatisticPane() {
        titleLabel = new Label("Статистика");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.DARKBLUE);

        energyDepletionDeathsLabel = new Label("Умерли от голода: 0");
        energyDepletionDeathsLabel.setFont(Font.font("Arial", FontPosture.REGULAR, 14));

        predationDeathsLabel = new Label("Съедено хищниками: 0");
        predationDeathsLabel.setFont(Font.font("Arial", FontPosture.REGULAR, 14));

        birthsLabel = new Label("Родилось клеток: 0");
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

        Line separator1 = createSeparator(Color.DARKBLUE);
        Line separator2 = createSeparator(Color.DARKBLUE);
        Line separator3 = createSeparator(Color.DARKBLUE);
        Line separator4 = createSeparator(Color.DARKBLUE);
        Line separator5 = createSeparator(Color.DARKBLUE);

        this.getChildren().addAll(
                titleLabel, separator1, energyDepletionDeathsLabel, predationDeathsLabel, birthsLabel,
                currentAnimalsLabel, separator2, maxAnimalSizeLabel, minAnimalSizeLabel, separator3,
                maxAnimalSpeedLabel, minAnimalSpeedLabel, separator4, maxAnimalRadiusLabel, minAnimalRadiusLabel, separator5
        );

        // Устанавливаем серый фон и отступы
        this.setStyle("-fx-background-color: lightgray; -fx-padding: 10; -fx-spacing: 10;");

        // Добавляем тень для более эстетичного вида
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.4, 0.4));

        this.setEffect(dropShadow);
    }

    private Line createSeparator(Color color) {
        Line separator = new Line(0, 0, 200, 0); // линия длиной 200 пикселей
        separator.setStroke(color);
        separator.setStrokeWidth(2);
        return separator;
    }

    public void updateEnergyDepletionDeaths(int count) {
        energyDepletionDeathsLabel.setText("Умерли от голода: " + count);
    }

    public void updatePredationDeaths(int count) {
        predationDeathsLabel.setText("Съедено хищниками: " + count);
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
        currentAnimalsLabel.setText("Клеток в симуляции: " + count);
    }
}
